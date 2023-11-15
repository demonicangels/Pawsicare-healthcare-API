package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.doctor;
import com.example.pawsicare.domain.role;
import com.example.pawsicare.domain.user;
import com.example.pawsicare.domain.managerinterfaces.doctorManager;
import com.example.pawsicare.persistence.userEntityConverter;
import com.example.pawsicare.persistence.entity.doctorEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class doctorManagerImpl implements doctorManager {

    private userEntityConverter converter;
    private final PasswordEncoder passwordEncoder;
    private final com.example.pawsicare.persistence.jpaRepositories.userRepository userRepository;
    private final com.example.pawsicare.persistence.jpaRepositories.doctorRepository doctorRepository;

    /**
     * @param doctor
     * @return doctor when created
     * @should return a filled in doctor object when the doctor is created
     */
    @Override
    public doctor createDoctor(doctor doctor) {
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
    public doctor updateDoctor(doctor doctor) {
        return converter.fromDoctorEntity((doctorEntity) userRepository.save(converter.toUserEntity(doctor)));
    }

    @Override
    public doctor getDoctor(long id) {
         return converter.fromDoctorEntity(userRepository.getUserEntityById(id).map(doctorEntity.class :: cast).orElse(null));
    }

    /**
     * @return doctors when asked for doctors
     * @should return a list with all doctors when doctors are present
     * @should return an empty list when no doctors are present
     */
    @Override
    public List<doctor> getDoctors() {
        List<doctor> returnedDoctors = new ArrayList<>();

        List<user> doctorEntities = userRepository.findByRole(1).stream().map( converter :: fromUserEntity).toList();

        doctorEntities.forEach(d -> returnedDoctors.add((doctor) d));

        return returnedDoctors;
    }

    @Override
    public void deleteDoctor(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<doctor> getDoctorsByField(String field) {
        List<doctor> fieldDoctors = new ArrayList<>();

        doctorRepository.getDoctorEntitiesByField(field).stream().map(
                converter :: fromDoctorEntity
        ).forEach(
                fieldDoctors :: add
        );

        return fieldDoctors;
    }
}
