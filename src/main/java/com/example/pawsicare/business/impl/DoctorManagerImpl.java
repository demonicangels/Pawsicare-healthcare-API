package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.persistence.converters.UserEntityConverter;
import com.example.pawsicare.persistence.entity.DoctorEntity;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.DoctorRepository;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
     * @should verify the repository method is called correctly
     * @should set the values of the variables from the object from the db when their values are null or empty
     */
    @RolesAllowed({"Doctor"})
    @Override
    public Doctor updateDoctor(Doctor doctor) {
        Optional<UserEntity> userEntity = userRepository.getUserEntityById(doctor.getId());

        if(!userEntity.isEmpty()){
            DoctorEntity doctorEntity = (DoctorEntity) userEntity.get();

           if(doctor.getEmail() == null || doctor.getEmail().isEmpty()){
               doctor.setEmail(doctorEntity.getEmail());
           }else if(doctor.getPhoneNumber() == null || doctor.getPhoneNumber().isEmpty()){
               doctor.setPhoneNumber(doctorEntity.getPhoneNumber());
           }

           if(doctor.getPassword() == null || doctor.getPassword().isEmpty()){
               doctor.setPassword(doctorEntity.getPassword());
           }else{
               String encodedPass = passwordEncoder.encode(doctor.getPassword());
               doctor.setPassword(encodedPass);
           }
        }
        userRepository.updateUserEntityById(doctor.getId(), doctor.getEmail(), doctor.getPhoneNumber(), doctor.getPassword());

       return getDoctor(doctor.getId());
    }

    /**
     * @param id
     * @return doctor
     * @should return doctor when one with matching id is found
     */
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

    /**
     * @param id
     * @should verify if the deleteById method of the repository is being called
     */
    @Override
    public void deleteDoctor(long id) {
        userRepository.deleteUserEntityById(id);
    }

    /**
     * @param field
     * @return list of doctors from the specified field
     * @should return a list of doctors from the specified field
     */
    @Override
    public List<Doctor> getDoctorsByField(String field) {

        return doctorRepository.getDoctorEntitiesByField(field)
                .map(list -> list.stream().map(converter::fromDoctorEntity).toList())
                .orElse(Collections.emptyList());
    }
}
