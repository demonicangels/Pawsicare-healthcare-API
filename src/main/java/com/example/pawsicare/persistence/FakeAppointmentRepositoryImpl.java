package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.Appointment;
import com.example.pawsicare.persistence.fakerepositoryinterfaces.AppointmentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FakeAppointmentRepositoryImpl implements AppointmentRepository {
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
