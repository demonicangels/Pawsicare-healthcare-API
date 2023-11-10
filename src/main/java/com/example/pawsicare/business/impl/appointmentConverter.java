package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.DTOs.appointmentDTO;
import com.example.pawsicare.domain.appointment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class appointmentConverter {

    private final com.example.pawsicare.business.impl.doctorConverter doctorConverter;
    private final com.example.pawsicare.business.impl.clientConverter clientConverter;
    private final com.example.pawsicare.business.impl.petConverter petConverter;


    public appointmentDTO toDTO (appointment appointment){
        return appointmentDTO.builder()
                .id(appointment.getId())
                .dateAndStart(appointment.getDateAndStart())
                .dateAndEnd(appointment.getDateAndEnd())
                .client(clientConverter.toDTO(appointment.getClient()))
                .doctor(doctorConverter.toDTO(appointment.getDoctor()))
                .pet(petConverter.toDTO(appointment.getPet()))
                .build();
    }


    public appointment fromDTO (appointmentDTO appointmentDTO){
        return appointment.builder()
                //.id(appointmentDTO.getId())
                .dateAndStart(appointmentDTO.getDateAndStart())
                .dateAndEnd(appointmentDTO.getDateAndEnd())
                .client(clientConverter.fromDTO(appointmentDTO.getClient()))
                .doctor(doctorConverter.fromDTO(appointmentDTO.getDoctor()))
                .pet(petConverter.fromDTO(appointmentDTO.getPet()))
                .build();
    }
}
