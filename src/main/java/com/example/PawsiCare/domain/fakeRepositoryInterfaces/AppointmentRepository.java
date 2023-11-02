package com.example.PawsiCare.domain.fakeRepositoryInterfaces;

import com.example.PawsiCare.domain.Appointment;

import java.util.List;

public interface AppointmentRepository {
    Appointment createAppointment(Appointment appointment);
    Appointment updateAppointment(long id, Appointment appointment);
    List<Appointment> getUsersAppointments(long userId);
    void deleteAppointment(long id);
}
