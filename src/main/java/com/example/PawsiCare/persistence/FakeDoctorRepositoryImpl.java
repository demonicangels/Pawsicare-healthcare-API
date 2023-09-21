package com.example.PawsiCare.persistence;

import com.example.PawsiCare.business.domain.Doctor;
import org.springframework.stereotype.Repository;
import com.example.PawsiCare.business.repositories.DoctorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeDoctorRepositoryImpl implements DoctorRepository {

    private final List<Doctor> doctors;
    private int doctorId = 1;

    public FakeDoctorRepositoryImpl(){
        this.doctors = new ArrayList<>();
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        doctor.setId(doctorId);
        doctorId++;

        doctors.add(doctor);
        return doctor;
    }

    @Override
    public Doctor updateDoctor(long id, Doctor doctor) {
        Optional<Doctor> doc = this.doctors.stream().filter(d -> d.getId() == id).findFirst();
        if(doc.isPresent()){
            int index = doctors.indexOf(doc.get());

            Doctor doctorDTO = doc.get();
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
    public Doctor getDoctor(long id) {
        Optional<Doctor> doc = this.doctors.stream().filter(d -> d.getId() == id).findFirst();
        if(doc.isPresent()){
            Doctor doctor = doc.get();
            return doctor;
        }
        return null;
    }

    @Override
    public List<Doctor> getDoctors() {
        return this.doctors;
    }

    @Override
    public void deleteDoctor(long id) {
        doctors.removeIf(d -> d.getId() == id);
    }
}
