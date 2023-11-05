package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.business.DTOs.ClientDTO;
import com.example.PawsiCare.business.DTOs.DoctorDTO;
import com.example.PawsiCare.domain.Client;
import com.example.PawsiCare.domain.Doctor;
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
                .build();
    }
}
