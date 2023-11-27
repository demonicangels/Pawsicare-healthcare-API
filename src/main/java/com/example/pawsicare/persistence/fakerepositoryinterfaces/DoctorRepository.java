package com.example.pawsicare.persistence.fakerepositoryinterfaces;

import com.example.pawsicare.domain.Doctor;


import java.util.List;

public interface DoctorRepository {
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(long id, Doctor doctor);

    Doctor getDoctor(long id);

    List<Doctor> getDoctors();
    void deleteDoctor(long id);
}
