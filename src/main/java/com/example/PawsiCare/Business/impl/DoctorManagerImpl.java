package com.example.PawsiCare.Business.impl;

import com.example.PawsiCare.Domain.Doctor;
import com.example.PawsiCare.Domain.managerInterfaces.DoctorManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.PawsiCare.Domain.repositoryInterfaces.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DoctorManagerImpl implements DoctorManager {

    private final DoctorRepository doctorRepository;

    /**
     * @param doctor
     * @return doctor when created
     * @should return a filled in doctor object when the doctor is created
     */
    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.createDoctor(doctor);
    }


    /**
     * @param id
     * @param doctor
     * @return updated doctor when updated
     * @should return a doctor object with updated fields
     */
    @Override
    public Doctor updateDoctor(long id, Doctor doctor) {
        return doctorRepository.updateDoctor(id, doctor);
    }

    @Override
    public Doctor getDoctor(long id) {
         return doctorRepository.getDoctor(id);
    }


    /**
     * @return doctors when asked for doctors
     * @should return a list with all doctors when doctors are present
     * @should return an empty list when no doctors are present
     */
    @Override
    public List<Doctor> getDoctors() {
        List<Doctor> returnedDoctors = new ArrayList<>();
        doctorRepository.getDoctors().forEach(
            returnedDoctors::add
        );
        return returnedDoctors;
    }

    @Override
    public void deleteDoctor(long id) {
        doctorRepository.deleteDoctor(id);
    }

    @Override
    public List<Doctor> getDoctorsByField(String field) {
        List<Doctor> fieldDoctors = new ArrayList<>();

        for (Doctor d : doctorRepository.getDoctors()) {
            if (d.getField().equals(field)) {
                fieldDoctors.add(d);
            }
        }

        return fieldDoctors;
    }

//    private DoctorDTO ToDTO(Doctor doctor){
//        DoctorDTO d = new DoctorDTO();
//        d.setId(doctor.getId());
//        d.setName(doctor.getName());
//        d.setField(doctor.getField());
//        d.setEmail(doctor.getEmail());
//        d.setAge(doctor.getAge());
//        d.setDescription(doctor.getDescription());
//        d.setPassword(doctor.getPassword());
//        d.setPhoneNumber(doctor.getPhoneNumber());
//        return d;
//    }

//    private Doctor FromDTO(DoctorDTO doctorDTO){
//        Doctor d = new Doctor();
//        d.setId(doctorDTO.getId());
//        d.setName(doctorDTO.getName());
//        d.setField(doctorDTO.getField());
//        d.setEmail(doctorDTO.getEmail());
//        d.setAge(doctorDTO.getAge());
//        d.setDescription(doctorDTO.getDescription());
//        d.setPassword(doctorDTO.getPassword());
//        d.setPhoneNumber(doctorDTO.getPhoneNumber());
//        return d;
//    }
}
