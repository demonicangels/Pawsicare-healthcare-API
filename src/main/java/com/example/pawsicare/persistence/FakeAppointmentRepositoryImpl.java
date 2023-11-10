package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.appointment;
import com.example.pawsicare.persistence.fakeRepositoryInterfaces.AppointmentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FakeAppointmentRepositoryImpl implements AppointmentRepository {
    @Override
    public appointment createAppointment(appointment appointment) {
        return null;
    }

    @Override
    public appointment updateAppointment(long id, appointment appointment) {
        return null;
    }

    @Override
    public List<appointment> getUsersAppointments(long userId) {
        return null;
    }

    @Override
    public void deleteAppointment(long id) {
        //still not implemented
    }
}
