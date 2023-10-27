package com.example.PawsiCare.Business.impl;

import com.example.PawsiCare.Domain.managerInterfaces.AppointmentManager;
import com.example.PawsiCare.Domain.Appointment;
import com.example.PawsiCare.Domain.repositoryInterfaces.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentManagerImpl implements AppointmentManager {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.createAppointment(appointment);
    }

    @Override
    public Appointment rescheduleAppointment(long id, Appointment appointment) {
        return appointmentRepository.updateAppointment(id,appointment);
    }

    @Override
    public List<Appointment> getUsersAppointments(long userId) {
        return appointmentRepository.getUsersAppointments(userId);
    }

    @Override
    public void cancelAppointment(long id) {
        appointmentRepository.deleteAppointment(id);
    }
}
