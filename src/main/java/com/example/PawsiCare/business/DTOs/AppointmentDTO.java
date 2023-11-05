package com.example.PawsiCare.business.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO {
    private Long id;
    private LocalDate date;
    private int time;
    private long clientId;
    private long doctorId;
    private long petId;
}
