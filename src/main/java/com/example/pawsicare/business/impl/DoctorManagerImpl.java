package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.entity.DoctorEntity;
import com.example.pawsicare.persistence.jpaRepositories.DoctorRepository;
import com.example.pawsicare.persistence.jpaRepositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorManagerImpl implements DoctorManager {


    private final UserEntityConverter converter;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param doctor
     * @return doctor when created
     * @should return a filled in doctor object when the doctor is created
     */
    @Override
    public Doctor createDoctor(Doctor doctor) {
        String encodedPass = passwordEncoder.encode(doctor.getPassword());
        doctor.setPassword(encodedPass);

        return converter.fromDoctorEntity(userRepository.save(converter.toDoctorEntity(doctor)));
    }

    /**
     * @param doctor
     * @return updated doctor when updated
     * @should return a doctor object with updated fields
     */
    @Override
    public Doctor updateDoctor(Doctor doctor) {
        return converter.fromDoctorEntity((DoctorEntity) userRepository.save(converter.toUserEntity(doctor)));
    }

    @Override
    public Doctor getDoctor(long id) {
         return converter.fromDoctorEntity(userRepository.getUserEntityById(id).map(DoctorEntity.class :: cast).orElse(null));
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