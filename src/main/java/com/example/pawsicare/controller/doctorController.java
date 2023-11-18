package com.example.pawsicare.controller;

import com.example.pawsicare.business.DTOs.doctorDTO;
import com.example.pawsicare.business.impl.doctorConverter;
import com.example.pawsicare.business.requests.createDoctorRequest;
import com.example.pawsicare.business.requests.updateDoctorRequest;
import com.example.pawsicare.business.responses.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/doctors")
@AllArgsConstructor
public class doctorController {

    private final com.example.pawsicare.domain.managerinterfaces.doctorManager doctorManager;
    private final doctorConverter converter;

    @RolesAllowed({"Client"})
    @GetMapping(params = "id")
    public ResponseEntity<getDoctorResponse> getDoctorById(@RequestParam(name = "id", required = false) long id){
        Optional<doctorDTO> doctor = Optional.ofNullable(converter.toDTO(doctorManager.getDoctor(id)));

        if(!doctor.isEmpty()){
            getDoctorResponse doctorResponse = getDoctorResponse.builder()
                    .doctor(doctor.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(doctorResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @RolesAllowed({"Client"})
    @GetMapping(params = "field")
    public ResponseEntity<getAllDoctorsResponse> getDoctorsByField(@RequestParam(name = "field", required = false) String field){
        Optional<List<doctorDTO>> doctorsByField = Optional.ofNullable(doctorManager.getDoctorsByField(field).stream()
                .map(converter :: toDTO)
                .toList());

        if(doctorsByField.isPresent()){

            getAllDoctorsResponse doctorsResponse = getAllDoctorsResponse.builder()
                    .doctors(doctorsByField.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(doctorsResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping()
    public ResponseEntity<getAllDoctorsResponse> getDoctors(){
        Optional<List<doctorDTO>> doctors = Optional.of(doctorManager.getDoctors().stream()
                .map(converter :: toDTO)
                .toList());

        if(doctors.isPresent()){
            getAllDoctorsResponse doctorsResponse = getAllDoctorsResponse.builder()
                    .doctors(doctors.get())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(doctorsResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PostMapping()
    public ResponseEntity<createDoctorResponse> registerDoctor(@RequestBody @Valid createDoctorRequest request){
        doctorDTO doctorDTO = com.example.pawsicare.business.DTOs.doctorDTO.builder()
                .name(request.getName())
                .password(request.getPassword())
                .description(request.getDescription())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .field(request.getField())
                .build();

        Optional<com.example.pawsicare.business.DTOs.doctorDTO> doc = Optional.ofNullable(converter.toDTO(doctorManager.createDoctor(converter.fromDTO(doctorDTO))));
        if(!doc.isEmpty()){
            createDoctorResponse doctorResponse = createDoctorResponse.builder()
                    .doctor(doctorDTO)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @RolesAllowed({"Doctor"})
    @PutMapping()
    public ResponseEntity<updateDoctorResponse> updateDoctor(@RequestParam(name = "id") long id, @RequestBody @Valid updateDoctorRequest request){
        doctorDTO doctorDTO = com.example.pawsicare.business.DTOs.doctorDTO.builder()
                .name(request.getName())
                .password(request.getPassword())
                .description(request.getDescription())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .field(request.getField())
                .build();

        Optional<com.example.pawsicare.business.DTOs.doctorDTO> doc = Optional.ofNullable(converter.toDTO(doctorManager.updateDoctor(converter.fromDTO(doctorDTO))));

        if(!doc.isEmpty()){

            updateDoctorResponse doctorResponse = updateDoctorResponse.builder()
                    .updatedDoctor(doctorDTO)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(doctorResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
    @RolesAllowed({"Doctor"})
    @DeleteMapping()
    public ResponseEntity<Void> deleteDoctor(@RequestParam(name = "id") long id){
        doctorManager.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
