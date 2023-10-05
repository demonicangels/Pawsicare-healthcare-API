package com.example.PawsiCare.persistence;

import com.example.PawsiCare.business.domain.Appointment;
import com.example.PawsiCare.business.repositories.AppointmentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {
    @Override
    public Appointment createAppointment(Appointment appointment) {
        return null;
    }

    @Override
    public Appointment updateAppointment(long id, Appointment appointment) {
        return null;
    }

    @Override
    public List<Appointment> getUsersAppointments(long userId) {
        return null;
    }

    @Override
    public void deleteAppointment(long id) {

    }
}
