package com.example.PawsiCare.persistence.impl;

import com.example.PawsiCare.persistence.DTOs.DoctorDTO;
import org.springframework.stereotype.Repository;
import com.example.PawsiCare.persistence.DoctorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeDoctorRepositoryImpl implements DoctorRepository {

    private final List<DoctorDTO> doctors;
    private int doctorId = 1;

    public FakeDoctorRepositoryImpl(){
        this.doctors = new ArrayList<>();
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctor) {
        doctor.setId(doctorId);
        doctorId++;

        doctors.add(doctor);
        return doctor;
    }

    @Override
    public DoctorDTO updateDoctor(long id, DoctorDTO doctor) {
        Optional<DoctorDTO> doc = this.doctors.stream().filter(d -> d.getId() == id).findFirst();
        if(doc.isPresent()){
            int index = doctors.indexOf(doc.get());

            DoctorDTO doctorDTO = doc.get();
            doctorDTO.setName(doctor.getName());
            doctorDTO.setField(doctor.getField());
            doctorDTO.setAge(doctor.getAge());
            doctorDTO.setDescription(doctor.getDescription());
            doctorDTO.setEmail(doctor.getEmail());
            doctorDTO.setPhoneNumber(doctor.getPhoneNumber());
            doctorDTO.setPassword(doctor.getPassword());


            doctors.set(index, doctorDTO);

            return doctorDTO;
        }
        return null;
    }

    @Override
    public DoctorDTO getDoctor(long id) {
        Optional<DoctorDTO> doc = this.doctors.stream().filter(d -> d.getId() == id).findFirst();
        if(doc.isPresent()){
            DoctorDTO doctor = doc.get();
            return doctor;
        }
        return null;
    }

    @Override
    public List<DoctorDTO> getDoctors() {
        return this.doctors;
    }

    @Override
    public void deleteDoctor(long id) {
        doctors.removeIf(d -> d.getId() == id);
    }
}
