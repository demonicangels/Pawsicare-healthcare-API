package com.example.PawsiCare.Domain.managerInterfaces;

import com.example.PawsiCare.Domain.Doctor;

import java.util.List;

public interface DoctorManager {

    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(long id, Doctor doctor);

    Doctor getDoctor(long id);

    List<Doctor> getDoctors();
    List<Doctor> getDoctorsByField(String field);
    void deleteDoctor(long id);
}
