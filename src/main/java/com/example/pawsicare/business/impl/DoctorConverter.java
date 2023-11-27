package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.domain.Doctor;
import org.springframework.stereotype.Service;

@Service
public class DoctorConverter {
    public DoctorDTO toDTO (Doctor doctor){

        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .birthday(doctor.getBirthday())
                .password(doctor.getPassword())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .description(doctor.getDescription())
                .field(doctor.getField())
                .image(doctor.getImage())
                .build();
    }

    public Doctor fromDTO (DoctorDTO doctorDTO){

        return Doctor.builder()
                .id(doctorDTO.getId())
                .name(doctorDTO.getName())
                .birthday(doctorDTO.getBirthday())
                .password(doctorDTO.getPassword())
                .email(doctorDTO.getEmail())
                .phoneNumber(doctorDTO.getPhoneNumber())
                .description(doctorDTO.getDescription())
                .field(doctorDTO.getField())
                .image(doctorDTO.getImage())
                .build();
    }
}
