package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.business.Doctor;
import com.example.PawsiCare.business.DoctorManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.PawsiCare.persistence.DTOs.DoctorDTO;
import com.example.PawsiCare.persistence.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DoctorManagerImpl implements DoctorManager {
    private final DoctorRepository doctorRepository;
    @Override
    public Doctor createDoctor(Doctor doctor) {
        return FromDTO(doctorRepository.createDoctor(ToDTO(doctor)));
    }

    @Override
    public Doctor updateDoctor(long id, Doctor doctor) {
        return FromDTO(doctorRepository.updateDoctor(id, ToDTO(doctor)));
    }

    @Override
    public Doctor getDoctor(long id) {
         return FromDTO(doctorRepository.getDoctor(id));
    }

    @Override
    public List<Doctor> getDoctors() {
        List<Doctor> returnedDoctors = new ArrayList<>();
        doctorRepository.getDoctors().forEach(d -> {
            returnedDoctors.add(FromDTO(d));
        });
        return returnedDoctors;
    }

    @Override
    public void deleteDoctor(long id) {
        doctorRepository.deleteDoctor(id);
    }

    @Override
    public List<Doctor> getDoctorsByField(String field) {
        List<Doctor> fieldDoctors = new ArrayList<>();

        for (DoctorDTO d : doctorRepository.getDoctors()) {
            if (d.getField().equals(field)) {
                fieldDoctors.add(FromDTO(d));
            }
        }

        return fieldDoctors;
    }

    private DoctorDTO ToDTO(Doctor doctor){
        DoctorDTO d = new DoctorDTO();
        d.setId(doctor.getId());
        d.setName(doctor.getName());
        d.setField(doctor.getField());
        d.setEmail(doctor.getEmail());
        d.setAge(doctor.getAge());
        d.setDescription(doctor.getDescription());
        d.setPassword(doctor.getPassword());
        d.setPhoneNumber(doctor.getPhoneNumber());
        return d;
    }

    private Doctor FromDTO(DoctorDTO doctorDTO){
        Doctor d = new Doctor();
        d.setId(doctorDTO.getId());
        d.setName(doctorDTO.getName());
        d.setField(doctorDTO.getField());
        d.setEmail(doctorDTO.getEmail());
        d.setAge(doctorDTO.getAge());
        d.setDescription(doctorDTO.getDescription());
        d.setPassword(doctorDTO.getPassword());
        d.setPhoneNumber(doctorDTO.getPhoneNumber());
        return d;
    }
}