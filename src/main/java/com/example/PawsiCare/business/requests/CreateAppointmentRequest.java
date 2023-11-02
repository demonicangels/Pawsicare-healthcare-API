package com.example.PawsiCare.business.requests;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest {
    private LocalDate date;
    private int time;
    private long clientId;
    private long doctorId;
    private long petId;
}
