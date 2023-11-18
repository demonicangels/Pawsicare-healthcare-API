package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.Appointment;
import com.example.pawsicare.persistence.entity.AppointmentEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.pawsicare.persistence.jpaRepositories.UserRepository;

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
