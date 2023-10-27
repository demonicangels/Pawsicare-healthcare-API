package com.example.PawsiCare.Domain.managerInterfaces;

import com.example.PawsiCare.Domain.Appointment;

import java.util.List;

public interface AppointmentManager {
    Appointment createAppointment(Appointment appointment);
    Appointment rescheduleAppointment(long id, Appointment appointment);
    List<Appointment> getUsersAppointments(long userId);
    void cancelAppointment(long id);
}
