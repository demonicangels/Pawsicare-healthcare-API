package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.business.DTOs.AppointmentDTO;
import com.example.PawsiCare.domain.Appointment;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AppointmentConverter {


    public AppointmentDTO toDTO (Appointment appointment){
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .date(appointment.getDate())
                .time(appointment.getTime())
                .clientId(appointment.getClientId())
                .doctorId(appointment.getDoctorId())
                .petId(appointment.getPetId())
                .build();
    }


    public Appointment fromDTO (AppointmentDTO appointmentDTO){
        return Appointment.builder()
                .id(appointmentDTO.getId())
                .date(appointmentDTO.getDate())
                .time(appointmentDTO.getTime())
                .clientId(appointmentDTO.getClientId())
                .doctorId(appointmentDTO.getDoctorId())
                .petId(appointmentDTO.getPetId())
                .build();
    }
}
