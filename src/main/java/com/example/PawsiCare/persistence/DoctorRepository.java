package com.example.PawsiCare.persistence;

import com.example.PawsiCare.persistence.DTOs.DoctorDTO;

import java.util.List;

public interface DoctorRepository {
    DoctorDTO createDoctor(DoctorDTO doctor);
    DoctorDTO updateDoctor(long id, DoctorDTO doctor);

    DoctorDTO getDoctor(long id);

    List<DoctorDTO> getDoctors();
    void deleteDoctor(long id);
}
