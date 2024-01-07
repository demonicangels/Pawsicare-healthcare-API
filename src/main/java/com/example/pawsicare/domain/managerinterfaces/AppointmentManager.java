package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.Appointment;
import com.example.pawsicare.domain.DayOfWeek;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentManager {

    List<Appointment> createDoctorSchedule(String token, DayOfWeek startDay, DayOfWeek endDay, LocalTime startTime, LocalTime endTime);
    void createAppointment(Appointment appointment);
    void rescheduleAppointment(Appointment appointment);
    Optional <List<Appointment>> getUsersAppointments(long userId);
    List<Appointment> getDoctorSchedule(long docId);
    void cancelAppointment(long id);
}
