package com.example.pawsicare.controller;

import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.business.exceptions.UserNotAuthenticatedException;
import com.example.pawsicare.business.impl.DoctorConverter;
import com.example.pawsicare.business.requests.CreateDoctorRequest;
import com.example.pawsicare.business.requests.UpdateDoctorRequest;
import com.example.pawsicare.business.responses.*;
import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.impl.AccessTokenDecoderEncoderImpl;
import com.example.pawsicare.domain.Role;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173",methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorManager doctorManager;
    private final DoctorConverter converter;
    private final AccessTokenDecoderEncoderImpl accessTokenService;
    private final UserEntityConverter userEntityConverter;
    private String errorMsg = "User not allowed!";
    private final UserRepository userRepository;

    @RolesAllowed({"Client","Doctor"})
    @GetMapping("/docInfo")
    public ResponseEntity<GetDoctorResponse> getDoctorById( @RequestParam("id") Long id,
                                                            @RequestParam("token") String token) throws UserNotAuthenticatedException {
        //only Clients and the Doctor himself are privy to a doctor's information

        AccessToken tokenClaims = accessTokenService.decode(token);
        Optional<User> userFound = userRepository.getUserEntityById(tokenClaims.getId())
                .map(userEntityConverter::fromUserEntity)
                .map(Optional::of)
                .orElse(Optional.empty());


        if (!userFound.isEmpty()){

            Long userId = tokenClaims.getId();
            boolean isDoctor = tokenClaims.hasRole(Role.Doctor.name());
            boolean isClient = tokenClaims.hasRole(Role.Client.name());

            if (userId.equals(id) && isDoctor || isClient){
                Optional<DoctorDTO> doctor = Optional.ofNullable(converter.toDTO(doctorManager.getDoctor(id)));

                if(!doctor.isEmpty()){
                    GetDoctorResponse doctorResponse = GetDoctorResponse.builder()
                            .doctor(doctor.get())
                            .build();

                    return ResponseEntity.status(HttpStatus.OK).body(doctorResponse);
                }
            }
        }
        throw new UserNotAuthenticatedException(errorMsg);

    }

    @GetMapping("/fields")
    public ResponseEntity<GetAllDoctorsResponse> getDoctorsByField(@RequestParam(name = "field", required = false) String field){

        //possibly make so only clients can see the doctors by work field
        Optional<List<DoctorDTO>> doctorsByField = Optional.ofNullable(doctorManager.getDoctorsByField(field).stream()
                .map(converter :: toDTO)
                .toList());

        if(!doctorsByField.isEmpty()){

            GetAllDoctorsResponse doctorsResponse = GetAllDoctorsResponse.builder()
                    .doctors(doctorsByField.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(doctorsResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping()
    public ResponseEntity<GetAllDoctorsResponse> getDoctors(){
        Optional<List<DoctorDTO>> doctors = Optional.of(doctorManager.getDoctors().stream()
                .map(converter :: toDTO)
                .toList());

        if(!doctors.get().isEmpty()){
            GetAllDoctorsResponse doctorsResponse = GetAllDoctorsResponse.builder()
                    .doctors(doctors.get())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(doctorsResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
    @PostMapping()
    public ResponseEntity<CreateDoctorResponse> registerDoctor(@RequestBody @Valid CreateDoctorRequest request){
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .name(request.getName())
                .password(request.getPassword())
                .description(request.getDescription())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .field(request.getField())
                .build();

        Optional<DoctorDTO> doc = Optional.ofNullable(converter.toDTO(doctorManager.createDoctor(converter.fromDTO(doctorDTO))));
        if(!doc.isEmpty()){
            CreateDoctorResponse doctorResponse = CreateDoctorResponse.builder()
                    .doctor(doctorDTO)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @RolesAllowed({"Doctor"})
    @PutMapping()
    public ResponseEntity<UpdateDoctorResponse> updateDoctor(@RequestBody @Valid UpdateDoctorRequest request) throws UserNotAuthenticatedException {

        //only the doctor himself can update his information

        AccessToken tokenClaims = accessTokenService.decode(request.getToken());

        Long userId = tokenClaims.getId();

        if(userId.equals(request.getId())) {
            DoctorDTO doctorDTO = DoctorDTO.builder()
                    .name(request.getName())
                    .password(request.getPassword())
                    .description(request.getDescription())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .field(request.getField())
                    .build();

            Optional<DoctorDTO> doc = Optional.ofNullable(converter.toDTO(doctorManager.updateDoctor(converter.fromDTO(doctorDTO))));

            if (!doc.isEmpty()) {

                UpdateDoctorResponse doctorResponse = UpdateDoctorResponse.builder()
                        .updatedDoctor(doctorDTO)
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(doctorResponse);
            }
        }
        throw new UserNotAuthenticatedException(errorMsg);
    }
    @RolesAllowed({"Doctor"})
    @DeleteMapping()
    public ResponseEntity<Void> deleteDoctor(@RequestParam(name = "id") Long id, @RequestParam(name = "token") String token) throws UserNotAuthenticatedException {
        AccessToken tokenClaims = accessTokenService.decode(token);

        Long userId = tokenClaims.getId();
        boolean isDoctor = tokenClaims.hasRole(Role.Doctor.name());

        if(userId.equals(id) && isDoctor){
            doctorManager.deleteDoctor(id);
            return ResponseEntity.noContent().build();
        }
        throw new UserNotAuthenticatedException(errorMsg);
    }
}
