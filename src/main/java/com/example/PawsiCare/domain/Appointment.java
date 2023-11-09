package com.example.PawsiCare.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    private long id;
    private LocalDateTime dateAndStart;
    private LocalDateTime dateAndEnd;
    private Client client;
    private Doctor doctor;
    private Pet pet;
}
