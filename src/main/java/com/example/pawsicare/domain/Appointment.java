package com.example.pawsicare.domain;

import lombok.*;

import java.time.LocalDateTime;

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
