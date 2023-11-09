package com.example.PawsiCare.domain.managerInterfaces;

import com.example.PawsiCare.domain.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentManager {
    Optional<Appointment> createAppointment(Appointment appointment);
    Optional<Appointment> rescheduleAppointment(long id, Appointment appointment);
    Optional <List<Appointment>> getUsersAppointments(long userId);
    Optional<List<Appointment>> getDoctorsAppointments(long doctorId);
    void cancelAppointment(long id);
}
