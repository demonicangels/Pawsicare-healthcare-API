package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.managerinterfaces.AppointmentManager;
import com.example.pawsicare.domain.Appointment;
import com.example.pawsicare.persistence.AppointmentEntityConverter;
import com.example.pawsicare.persistence.jparepositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentManagerImpl implements AppointmentManager {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentEntityConverter converter;

    /**
     * @param appointment
     * @should return a fully created appointment with all fields
     * @return created appointment
     */
    @Override
    public Optional<Appointment> createAppointment(Appointment appointment) {
        return Optional.of(converter.fromEntity(appointmentRepository.save(converter.toEntity(appointment))));
    }

    /**
     * @param appointment
     * @should update the correct appointment with the correct values
     * @return updated appointment
     */
    @Override
    public Optional<Appointment> rescheduleAppointment(Appointment appointment) {
        return Optional.of(converter.fromEntity(appointmentRepository.save(converter.toEntity(appointment))));
    }

    /**
     * @param userId
     * @should  return all users appointments when present
     * @should  return an empty list if the user has no appointments
     * @return list of appointments
     *
     */
    @Override
    public Optional<List<Appointment>> getUsersAppointments(long userId) {
        return Optional.of(appointmentRepository.findAppointmentEntitiesByClient_IdOrDoctor_Id(userId,userId).stream().map(converter :: fromEntity).toList());
    }

    /**
     * @param id
     * @should verify if the deleteById method of the repository is being called
     */
    @Override
    public void cancelAppointment(long id) {
        appointmentRepository.deleteById(id);
    }
}
