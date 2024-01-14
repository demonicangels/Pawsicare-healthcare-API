package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.AccessTokenDecoder;
import com.example.pawsicare.domain.DayOfWeek;
import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.managerinterfaces.AppointmentManager;
import com.example.pawsicare.domain.Appointment;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.persistence.converters.AppointmentEntityConverter;
import com.example.pawsicare.persistence.converters.PetEntityConverter;
import com.example.pawsicare.persistence.converters.UserEntityConverter;
import com.example.pawsicare.persistence.jparepositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentManagerImpl implements AppointmentManager {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentEntityConverter converter;
    private final AccessTokenDecoder accessTokenDecoder;
    private final DoctorManager doctorManager;
    private final UserEntityConverter userEntityConverter;
    private final PetEntityConverter petEntityConverter;


    private int numberOfDaysInMonth = LocalDate.now().lengthOfMonth();
    private int appointmentDurationInMinutes = 60;


    /**
     * @param docId
     * @param startDay
     * @param endDay
     * @param startTime
     * @param endTime
     * @should a list with created appointments in the specified range
     * @return a list with the doctors automatically created schedule
     */
    @Override
    public List<Appointment> createDoctorSchedule(long docId, DayOfWeek startDay, DayOfWeek endDay, LocalTime startTime, LocalTime endTime) {

        Doctor doctor = doctorManager.getDoctor(docId);

        LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

        YearMonth yearMonth = YearMonth.of(currentDateTime.getYear(),currentDateTime.getMonth());
        numberOfDaysInMonth = yearMonth.lengthOfMonth();

        LocalDateTime endDateTime = currentDateTime.plusDays(numberOfDaysInMonth);

        List<Appointment> appointments = new ArrayList<>();

        while (currentDateTime.isBefore(endDateTime) || currentDateTime.equals(endDateTime)) {
            DayOfWeek dayOfWeek = DayOfWeek.values()[currentDateTime.get(ChronoField.DAY_OF_WEEK) - 1];

            if (dayOfWeek.compareTo(startDay) >= 0 && dayOfWeek.compareTo(endDay) <= 0) {
                if (currentDateTime.getHour() >= startTime.getHour() && currentDateTime.getHour() < endTime.getHour()) {
                    LocalDateTime appStart = currentDateTime;
                    LocalDateTime appEnd = currentDateTime.plusMinutes(appointmentDurationInMinutes);
                    appointments.add(Appointment.builder()
                            .dateAndStart(appStart)
                            .dateAndEnd(appEnd)
                            .doctor(doctor).build());
                }
            }

            currentDateTime = currentDateTime.plusHours(1);
        }

        for (Appointment a: appointments){

            appointmentRepository.save(converter.toEntity(a));
        }

        return appointments;
    }

    /**
     * @param appointment
     * @should return a fully created appointment with all fields
     * @return created appointment
     */
    @Override
    public void createAppointment(Appointment appointment) {

        appointmentRepository.updateAppointmentEntityByDateAndAndDateAndStartAndDoctor(appointment.getDateAndStart(),
                userEntityConverter.toDoctorEntity(appointment.getDoctor()),
                userEntityConverter.toClientEntity(appointment.getClient()),
                petEntityConverter.toEntity(appointment.getPet()));
    }

    /**
     * @param appointment
     * @should update the correct appointment with the correct values
     * @return updated appointment
     */
    @Override
    public void rescheduleAppointment(Appointment appointment) {
        appointmentRepository.updateAppointmentEntityByDoctorAndClientAndPet(appointment.getDateAndStart(),
                appointment.getDateAndEnd(),
                userEntityConverter.toDoctorEntity(appointment.getDoctor()),
                userEntityConverter.toClientEntity(appointment.getClient()),
                petEntityConverter.toEntity(appointment.getPet()));

        appointmentRepository.updateAppointmentEntityByIdAndDoctorAndClientAndPet(appointment.getId(),
                userEntityConverter.toDoctorEntity(appointment.getDoctor()));
    }

    /**
     * @param userId
     * @should  return all users appointments when present
     * @should  return an empty list if the user has no appointments
     * @return list of appointments
     *
     */
    @Override
    public Optional<List<Appointment>> getUsersAppointments(long userId) {
        return Optional.of(appointmentRepository.findAppointmentEntitiesByClient_IdOrDoctor_Id(userId,userId).stream().map(converter :: fromEntity).toList());
    }

    /**
     * @param docId
     * @should  return all doctors available slots when present
     * @should  return an empty list if the doctor has no available slots left
     * @return list of appointments
     *
     */
    @Override
    public List<Appointment> getDoctorSchedule(long docId) {

       return appointmentRepository.getDocSchedule(docId)
               .stream()
               .map(converter::fromEntity)
               .toList();
    }

    /**
     * @param id
     * @should verify if the deleteById method of the repository is being called
     */
    @Override
    public void cancelAppointment(long id) {
        appointmentRepository.deleteById(id);
    }


}
