package com.example.PawsiCare.controller;

import com.example.PawsiCare.business.domain.Doctor;
import com.example.PawsiCare.business.DoctorManager;
import com.example.PawsiCare.business.requests.CreateDoctorRequest;
import com.example.PawsiCare.business.requests.UpdateDoctorRequest;
import com.example.PawsiCare.business.responses.*;
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

    @GetMapping(params = "id")
    public ResponseEntity<GetDoctorResponse> getDoctorById(@RequestParam(name = "id", required = false) long id){
        Optional<Doctor> doctor = Optional.ofNullable(doctorManager.getDoctor(id));

        if(doctor.isPresent()){
            GetDoctorResponse doctorResponse = GetDoctorResponse.builder()
                    .doctor(doctor.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(doctorResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(params = "field")
    public ResponseEntity<GetAllDoctorsResponse> getDoctorsByField(@RequestParam(name = "field", required = false) String field){
        Optional<List<Doctor>> doctorsByField = Optional.ofNullable(doctorManager.getDoctorsByField(field));
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
        Optional<List<Doctor>> doctors = Optional.ofNullable(doctorManager.getDoctors());
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
        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .password(request.getPassword())
                .description(request.getDescription())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .field(request.getField())
                .build();

        Optional<Doctor> doc = Optional.ofNullable(doctorManager.createDoctor(doctor));
        if(doc.isPresent()){
            CreateDoctorResponse doctorResponse = CreateDoctorResponse.builder()
                    .doctor(doctor)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping()
    public ResponseEntity<UpdateDoctorResponse> updateDoctor(@RequestParam(name = "id") long id, @RequestBody @Valid UpdateDoctorRequest request){
        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .password(request.getPassword())
                .description(request.getDescription())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .field(request.getField())
                .build();

        Optional<Doctor> doc = Optional.ofNullable(doctorManager.updateDoctor(id, doctor));

        if(doc.isPresent()){

            UpdateDoctorResponse doctorResponse = UpdateDoctorResponse.builder()
                    .updatedDoctor(doctor)
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
