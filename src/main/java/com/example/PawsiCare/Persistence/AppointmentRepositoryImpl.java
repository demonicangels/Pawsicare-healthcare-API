package com.example.PawsiCare.Persistence;

import com.example.PawsiCare.Domain.Appointment;
import com.example.PawsiCare.Domain.repositoryInterfaces.AppointmentRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

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
        //still not implemented
    }
}
