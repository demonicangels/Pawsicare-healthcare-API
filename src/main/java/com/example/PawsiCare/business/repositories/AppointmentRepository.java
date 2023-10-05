package com.example.PawsiCare.business.repositories;

import com.example.PawsiCare.business.domain.Appointment;

import java.util.List;

public interface AppointmentRepository {
    Appointment createAppointment(Appointment appointment);
    Appointment updateAppointment(long id, Appointment appointment);
    List<Appointment> getUsersAppointments(long userId);
    void deleteAppointment(long id);
}
