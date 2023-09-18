package com.example.PawsiCare.business;

import com.example.PawsiCare.business.domain.Doctor;

import java.util.List;

public interface DoctorManager {
    // create, delete, update, get doctor by id + get all doctors

    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(long id, Doctor doctor);

    Doctor getDoctor(long id);

    List<Doctor> getDoctors();
    void deleteDoctor(long id);

    List<Doctor> getDoctorsByField(String field);






}
