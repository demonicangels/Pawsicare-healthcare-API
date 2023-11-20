package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.DTOs.AppointmentDTO;
import com.example.pawsicare.domain.Appointment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentConverter {

    private final DoctorConverter doctorConverter;
    private final ClientConverter clientConverter;
    private final PetConverter petConverter;

    /**
     * @param appointment
     * @return appointmentDTO when performed the conversion
     * @should return an AppointmentDTO after successful conversion
     */
    public AppointmentDTO toDTO (Appointment appointment){
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .dateAndStart(appointment.getDateAndStart())
                .dateAndEnd(appointment.getDateAndEnd())
                .client(clientConverter.toDTO(appointment.getClient()))
                .doctor(doctorConverter.toDTO(appointment.getDoctor()))
                .pet(petConverter.toDTO(appointment.getPet()))
                .build();
    }

    /**
     * @param appointmentDTO
     * @return appointment when performed the conversion
     * @should return an Appointment after successful conversion
     */

    public Appointment fromDTO (AppointmentDTO appointmentDTO){
        return Appointment.builder()
                //.id(appointmentDTO.getId())
                .dateAndStart(appointmentDTO.getDateAndStart())
                .dateAndEnd(appointmentDTO.getDateAndEnd())
                .client(clientConverter.fromDTO(appointmentDTO.getClient()))
                .doctor(doctorConverter.fromDTO(appointmentDTO.getDoctor()))
                .pet(petConverter.fromDTO(appointmentDTO.getPet()))
                .build();
    }
}
