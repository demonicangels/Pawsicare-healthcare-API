package com.example.pawsicare.controller;

import com.example.pawsicare.business.DTOs.DoctorDTO;
import com.example.pawsicare.business.impl.DoctorConverter;
import com.example.pawsicare.domain.managerInterfaces.DoctorManager;
import com.example.pawsicare.business.requests.CreateDoctorRequest;
import com.example.pawsicare.business.requests.UpdateDoctorRequest;
import com.example.pawsicare.business.responses.*;
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
public class DoctorController {

    private final DoctorManager doctorManager;
    private final DoctorConverter converter;

    @GetMapping(params = "id")
    public ResponseEntity<GetDoctorResponse> getDoctorById(@RequestParam(name = "id", required = false) long id){
        Optional<DoctorDTO> doctor = Optional.ofNullable(converter.toDTO(doctorManager.getDoctor(id)));

        if(!doctor.isEmpty()){
            GetDoctorResponse doctorResponse = GetDoctorResponse.builder()
                    .doctor(doctor.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(doctorResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(params = "field")
    public ResponseEntity<GetAllDoctorsResponse> getDoctorsByField(@RequestParam(name = "field", required = false) String field){
        Optional<List<DoctorDTO>> doctorsByField = Optional.ofNullable(doctorManager.getDoctorsByField(field).stream()
                .map(converter :: toDTO)
                .toList());
        if(doctorsByField.isPresent()){

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

        if(doctors.isPresent()){
            GetAllDoctorsResponse doctorsResponse = GetAllDoctorsResponse.builder()
                    .doctors(doctors.get())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(doctorsResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

    @PutMapping()
    public ResponseEntity<UpdateDoctorResponse> updateDoctor(@RequestParam(name = "id") long id, @RequestBody @Valid UpdateDoctorRequest request){
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .name(request.getName())
                .password(request.getPassword())
                .description(request.getDescription())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .field(request.getField())
                .build();

        Optional<DoctorDTO> doc = Optional.ofNullable(converter.toDTO(doctorManager.updateDoctor(converter.fromDTO(doctorDTO))));

        if(!doc.isEmpty()){

            UpdateDoctorResponse doctorResponse = UpdateDoctorResponse.builder()
                    .updatedDoctor(doctorDTO)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(doctorResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteDoctor(@RequestParam(name = "id") long id){
        doctorManager.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
