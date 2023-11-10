package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.DTOs.doctorDTO;
import com.example.pawsicare.domain.doctor;
import org.springframework.stereotype.Service;

@Service
public class doctorConverter {
    public doctorDTO toDTO (doctor doctor){

        return doctorDTO.builder()
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

    public doctor fromDTO (doctorDTO doctorDTO){

        return doctor.builder()
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
