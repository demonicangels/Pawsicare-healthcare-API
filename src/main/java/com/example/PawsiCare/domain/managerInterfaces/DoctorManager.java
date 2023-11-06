package com.example.PawsiCare.domain.managerInterfaces;

import com.example.PawsiCare.domain.Doctor;

import java.util.List;

public interface DoctorManager {

    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(Doctor doctor);

    Doctor getDoctor(long id);

    List<Doctor> getDoctors();
    List<Doctor> getDoctorsByField(String field);
    void deleteDoctor(long id);
}
