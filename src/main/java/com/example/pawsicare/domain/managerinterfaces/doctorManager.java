package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.Doctor;

import java.util.List;

public interface doctorManager {

    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(Doctor doctor);

    Doctor getDoctor(long id);

    List<Doctor> getDoctors();
    List<Doctor> getDoctorsByField(String field);
    void deleteDoctor(long id);
}
