package com.example.PawsiCare.Domain.repositoryInterfaces;

import com.example.PawsiCare.Domain.Doctor;


import java.util.List;

public interface DoctorRepository {
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(long id, Doctor doctor);

    Doctor getDoctor(long id);

    List<Doctor> getDoctors();
    void deleteDoctor(long id);
}
