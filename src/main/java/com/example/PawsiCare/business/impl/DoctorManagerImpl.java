package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.domain.Doctor;
import com.example.PawsiCare.domain.User;
import com.example.PawsiCare.domain.managerInterfaces.DoctorManager;
import com.example.PawsiCare.persistence.UserEntityConverter;
import com.example.PawsiCare.persistence.entity.DoctorEntity;
import com.example.PawsiCare.persistence.jpaRepositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.PawsiCare.persistence.jpaRepositories.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DoctorManagerImpl implements DoctorManager {

    private UserEntityConverter converter;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    /**
     * @param doctor
     * @return doctor when created
     * @should return a filled in doctor object when the doctor is created
     */
    @Override
    public Doctor createDoctor(Doctor doctor) {
        return converter.fromDoctorEntity(userRepository.save(converter.toDoctorEntity(doctor)));
    }

    /**
     * @param id
     * @param doctor
     * @return updated doctor when updated
     * @should return a doctor object with updated fields
     */
    @Override
    public Doctor updateDoctor(long id, Doctor doctor) {
        return converter.fromDoctorEntity((DoctorEntity) userRepository.save(converter.toUserEntity(doctor)));
    }

    @Override
    public Doctor getDoctor(long id) {
         return converter.fromDoctorEntity((DoctorEntity)userRepository.getUserEntityById(id).get());
    }


    /**
     * @return doctors when asked for doctors
     * @should return a list with all doctors when doctors are present
     * @should return an empty list when no doctors are present
     */
    @Override
    public List<Doctor> getDoctors() {
        List<Doctor> returnedDoctors = new ArrayList<>();

        List<User> doctorEntities = userRepository.findByRole(1).stream().map( converter :: fromUserEntity).toList();

        doctorEntities.forEach(d -> returnedDoctors.add((Doctor) d));

        return returnedDoctors;
    }

    @Override
    public void deleteDoctor(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Doctor> getDoctorsByField(String field) {
        List<Doctor> fieldDoctors = new ArrayList<>();

        doctorRepository.getDoctorEntitiesByField(field).stream().map(
                converter :: fromDoctorEntity
        ).forEach(
                fieldDoctors :: add
        );

        return fieldDoctors;
    }
}
