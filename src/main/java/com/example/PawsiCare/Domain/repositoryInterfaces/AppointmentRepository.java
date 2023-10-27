package com.example.PawsiCare.Domain.repositoryInterfaces;

import com.example.PawsiCare.Domain.Appointment;

import java.util.List;

public interface AppointmentRepository {
    Appointment createAppointment(Appointment appointment);
    Appointment updateAppointment(long id, Appointment appointment);
    List<Appointment> getUsersAppointments(long userId);
    void deleteAppointment(long id);
}
