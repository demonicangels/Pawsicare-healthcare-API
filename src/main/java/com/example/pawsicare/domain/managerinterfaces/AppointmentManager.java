package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentManager {
    Optional<Appointment> createAppointment(Appointment appointment);
    Optional<Appointment> rescheduleAppointment(Appointment appointment);
    Optional <List<Appointment>> getUsersAppointments(long userId);
    void cancelAppointment(long id);
}
