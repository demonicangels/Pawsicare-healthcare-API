package com.example.pawsicare.business.converters;

import com.example.pawsicare.business.dto.AppointmentDTO;
import com.example.pawsicare.domain.Appointment;
import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.config.ExcludeFromCodeCoverage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AppointmentConverter {

    private final DoctorConverter doctorConverter;
    private final ClientConverter clientConverter;
    private final PetConverter petConverter;

    /**
     * @param appointment
     * @return appointmentDTO when performed the conversion
     * @should return an AppointmentDTO after successful conversion
     */
    public AppointmentDTO toDTO (Appointment appointment){
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .dateAndStart(appointment.getDateAndStart())
                .dateAndEnd(appointment.getDateAndEnd())
                .client(clientConverter.toDTO(appointment.getClient() == null ? new Client() : appointment.getClient()))
                .doctor(doctorConverter.toDTO(appointment.getDoctor()))
                .pet(petConverter.toDTO(appointment.getPet()))
                .build();
    }

    /**
     * @param appointmentDTO
     * @return appointment when performed the conversion
     * @should return an Appointment after successful conversion
     */

    public Appointment fromDTO (AppointmentDTO appointmentDTO){
        return Appointment.builder()
                //.id(appointmentDTO.getId())
                .dateAndStart(appointmentDTO.getDateAndStart())
                .dateAndEnd(appointmentDTO.getDateAndEnd())
                .client(clientConverter.fromDTO(appointmentDTO.getClient()))
                .doctor(doctorConverter.fromDTO(appointmentDTO.getDoctor()))
                .pet(petConverter.fromDTO(appointmentDTO.getPet()))
                .build();
    }

    /**
     * @param date
     * @param start
     * @return LocalDateTime equivalent to a passed string
     * @should return LocalDateTime object after successful conversion from string
     */
    @ExcludeFromCodeCoverage
    public LocalDateTime dateAndTime(String date,String start){
        LocalDate date1 = LocalDate.parse(date);
        LocalTime start1 = LocalTime.parse(start);
        return LocalDateTime.of(date1,start1);
    }

    public LocalTime fromStringToTime(String time){
        LocalTime time1 = null;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
            time1 = LocalTime.parse(time, formatter);
        }catch(Exception e){
            e.printStackTrace();
        }
        return time1;
    }
}
