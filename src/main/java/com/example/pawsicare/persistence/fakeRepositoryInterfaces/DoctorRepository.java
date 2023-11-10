package com.example.pawsicare.persistence.fakeRepositoryInterfaces;

import com.example.pawsicare.domain.doctor;


import java.util.List;

public interface DoctorRepository {
    doctor createDoctor(doctor doctor);
    doctor updateDoctor(long id, doctor doctor);

    doctor getDoctor(long id);

    List<doctor> getDoctors();
    void deleteDoctor(long id);
}
