package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.doctor;

import java.util.List;

public interface doctorManager {

    doctor createDoctor(doctor doctor);
    doctor updateDoctor(doctor doctor);

    doctor getDoctor(long id);

    List<doctor> getDoctors();
    List<doctor> getDoctorsByField(String field);
    void deleteDoctor(long id);
}
