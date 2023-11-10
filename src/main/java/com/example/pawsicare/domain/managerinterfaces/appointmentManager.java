package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.appointment;

import java.util.List;
import java.util.Optional;

public interface appointmentManager {
    Optional<appointment> createAppointment(appointment appointment);
    Optional<appointment> rescheduleAppointment(long id, appointment appointment);
    Optional <List<appointment>> getUsersAppointments(long userId);
    void cancelAppointment(long id);
}
