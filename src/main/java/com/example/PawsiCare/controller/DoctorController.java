package com.example.PawsiCare.controller;

import com.example.PawsiCare.business.Doctor;
import com.example.PawsiCare.business.DoctorManager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/doctors")
@AllArgsConstructor
public class DoctorController {

    private final DoctorManager doctorManager;


    @GetMapping(params = "id")
    public ResponseEntity<Doctor> getDoctorById(@RequestParam(name = "id", required = false) long id){
        Optional<Doctor> doctor = Optional.ofNullable(doctorManager.getDoctor(id));
        if(doctor.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(doctor.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(params = "field")
    public ResponseEntity<List<Doctor>> getDoctorsByField(@RequestParam(name = "field", required = false) String field){
        Optional<List<Doctor>> doctorsByField = Optional.ofNullable(doctorManager.getDoctorsByField(field));
        if(doctorsByField.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(doctorsByField.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping()
    public ResponseEntity<List<Doctor>> getDoctors(){
        Optional<List<Doctor>> doctors = Optional.ofNullable(doctorManager.getDoctors());
        if(doctors.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(doctors.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping()
    public ResponseEntity<Doctor> registerDoctor(@RequestBody Doctor doctor){
        Optional<Doctor> doc = Optional.ofNullable(doctorManager.createDoctor(doctor));
        if(doc.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(doc.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping()
    public ResponseEntity<Doctor> updateDoctor(@RequestParam(name = "id") long id, @RequestBody Doctor doctor){
        Optional<Doctor> doc = Optional.ofNullable(doctorManager.updateDoctor(id, doctor));
        if(doc.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(doc.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteDoctor(@RequestParam(name = "id") long id){
        doctorManager.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

}
