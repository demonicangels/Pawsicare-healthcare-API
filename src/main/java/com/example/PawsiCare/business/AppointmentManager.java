package com.example.PawsiCare.business;

import com.example.PawsiCare.business.domain.Appointment;
import com.example.PawsiCare.business.domain.Doctor;

import java.util.List;

public interface AppointmentManager {
    Appointment createAppointment(Appointment appointment);
    Appointment rescheduleAppointment(long id, Appointment appointment);
    List<Appointment> getUsersAppointments(long userId);
    void cancelAppointment(long id);
}
