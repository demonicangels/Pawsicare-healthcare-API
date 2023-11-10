package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.appointment;
import com.example.pawsicare.persistence.entity.appointmentEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class appointmentEntityConverter {

    private final com.example.pawsicare.persistence.jpaRepositories.userRepository userRepository;
    private final userEntityConverter converter;
    private final com.example.pawsicare.persistence.petEntityConverter petEntityConverter;


    public appointment fromEntity(appointmentEntity appointment){
        return com.example.pawsicare.domain.appointment.builder()
                .id(appointment.getId())
                .dateAndStart(appointment.getDateAndStart())
                .dateAndEnd(appointment.getDateAndEnd())
                .client(converter.fromClientEntity(appointment.getClient()))
                .doctor(converter.fromDoctorEntity(appointment.getDoctor()))
                .pet(petEntityConverter.fromEntity(appointment.getPet()))
                .build();
    }

    public appointmentEntity toEntity (appointment appointment){
        return appointmentEntity.builder()
                //.id(appointment.getId())
                .dateAndStart(appointment.getDateAndStart())
                .dateAndEnd(appointment.getDateAndEnd())
                .client(converter.toClientEntity(appointment.getClient()))
                .doctor(converter.toDoctorEntity(appointment.getDoctor()))
                .pet(petEntityConverter.toEntity(appointment.getPet()))
                .build();
    }
}
