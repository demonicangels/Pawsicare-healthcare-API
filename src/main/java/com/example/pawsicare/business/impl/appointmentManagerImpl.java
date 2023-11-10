package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.managerinterfaces.appointmentManager;
import com.example.pawsicare.domain.appointment;
import com.example.pawsicare.persistence.appointmentEntityConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class appointmentManagerImpl implements appointmentManager {

    private final com.example.pawsicare.persistence.jpaRepositories.appointmentRepository appointmentRepository;
    private final appointmentEntityConverter converter;

    @Override
    public Optional<appointment> createAppointment(appointment appointment) {
        return Optional.of(converter.fromEntity(appointmentRepository.save(converter.toEntity(appointment))));
    }

    @Override
    public Optional<appointment> rescheduleAppointment(long id, appointment appointment) {
        return Optional.of(converter.fromEntity(appointmentRepository.save(converter.toEntity(appointment))));
    }

    @Override
    public Optional<List<appointment>> getUsersAppointments(long userId) {
        //TODO fix this method the repository returns null
        Optional<List<appointment>> appointments = Optional.of(appointmentRepository.findAppointmentEntitiesByClient_IdOrDoctor_Id(userId,userId).stream().map(converter :: fromEntity).toList());
        return appointments;
    }

    @Override
    public void cancelAppointment(long id) {
        appointmentRepository.deleteById(id);
    }
}
