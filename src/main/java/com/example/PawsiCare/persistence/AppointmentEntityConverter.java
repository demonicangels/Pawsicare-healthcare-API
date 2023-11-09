package com.example.PawsiCare.persistence;

import com.example.PawsiCare.business.impl.ClientConverter;
import com.example.PawsiCare.business.impl.DoctorConverter;
import com.example.PawsiCare.business.impl.PetConverter;
import com.example.PawsiCare.domain.Appointment;
import com.example.PawsiCare.domain.Client;
import com.example.PawsiCare.persistence.entity.AppointmentEntity;
import com.example.PawsiCare.persistence.entity.ClientEntity;
import com.example.PawsiCare.persistence.entity.DoctorEntity;
import com.example.PawsiCare.persistence.entity.UserEntity;
import com.example.PawsiCare.persistence.jpaRepositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@AllArgsConstructor
public class AppointmentEntityConverter {

    private final UserRepository userRepository;
    private final UserEntityConverter converter;
    private final PetEntityConverter petEntityConverter;


    public Appointment fromEntity(AppointmentEntity appointment){
        return Appointment.builder()
                .id(appointment.getId())
                .dateAndStart(appointment.getDateAndStart())
                .dateAndEnd(appointment.getDateAndEnd())
                .client(converter.fromClientEntity(appointment.getClient()))
                .doctor(converter.fromDoctorEntity(appointment.getDoctor()))
                .pet(petEntityConverter.fromEntity(appointment.getPet()))
                .build();
    }

    public AppointmentEntity toEntity (Appointment appointment){
        return AppointmentEntity.builder()
                //.id(appointment.getId())
                .dateAndStart(appointment.getDateAndStart())
                .dateAndEnd(appointment.getDateAndEnd())
                .client(converter.toClientEntity(appointment.getClient()))
                .doctor(converter.toDoctorEntity(appointment.getDoctor()))
                .pet(petEntityConverter.toEntity(appointment.getPet()))
                .build();
    }
}
