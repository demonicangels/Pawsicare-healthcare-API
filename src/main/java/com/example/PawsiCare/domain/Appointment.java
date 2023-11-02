package com.example.PawsiCare.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    private long id;
    private LocalDate date;
    private int time;
    private long clientId;
    private long doctorId;
    private long petId;
}
