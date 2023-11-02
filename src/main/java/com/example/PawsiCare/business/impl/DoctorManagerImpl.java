package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.domain.Doctor;
import com.example.PawsiCare.domain.managerInterfaces.DoctorManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.PawsiCare.domain.fakeRepositoryInterfaces.DoctorRepository;

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
}
