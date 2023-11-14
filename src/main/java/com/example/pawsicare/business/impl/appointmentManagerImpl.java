package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.managerinterfaces.appointmentManager;
import com.example.pawsicare.domain.Appointment;
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
    public Optional<Appointment> createAppointment(Appointment appointment) {
        return Optional.of(converter.fromEntity(appointmentRepository.save(converter.toEntity(appointment))));
    }

    @Override
    public Optional<Appointment> rescheduleAppointment(long id, Appointment appointment) {
        return Optional.of(converter.fromEntity(appointmentRepository.save(converter.toEntity(appointment))));
    }

    @Override
    public Optional<List<Appointment>> getUsersAppointments(long userId) {
        //TODO fix this method the repository returns null
        Optional<List<Appointment>> appointments = Optional.of(appointmentRepository.findAppointmentEntitiesByClient_IdOrDoctor_Id(userId,userId).stream().map(converter :: fromEntity).toList());
        return appointments;
    }

    @Override
    public void cancelAppointment(long id) {
        appointmentRepository.deleteById(id);
    }
}
