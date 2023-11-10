package com.example.pawsicare.persistence.fakeRepositoryInterfaces;

import com.example.pawsicare.domain.appointment;

import java.util.List;

public interface AppointmentRepository {
    appointment createAppointment(appointment appointment);
    appointment updateAppointment(long id, appointment appointment);
    List<appointment> getUsersAppointments(long userId);
    void deleteAppointment(long id);
}
