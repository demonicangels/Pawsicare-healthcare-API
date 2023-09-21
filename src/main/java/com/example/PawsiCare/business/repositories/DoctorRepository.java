package com.example.PawsiCare.business.repositories;

import com.example.PawsiCare.business.domain.Doctor;


import java.util.List;

public interface DoctorRepository {
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(long id, Doctor doctor);

    Doctor getDoctor(long id);

    List<Doctor> getDoctors();
    void deleteDoctor(long id);
}
