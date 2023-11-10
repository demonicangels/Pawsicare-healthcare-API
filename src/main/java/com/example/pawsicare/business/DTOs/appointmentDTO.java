package com.example.pawsicare.business.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class appointmentDTO {
    private Long id;
    private LocalDateTime dateAndStart;
    private LocalDateTime dateAndEnd;
    private clientDTO client;
    private doctorDTO doctor;
    private petDTO pet;
}
