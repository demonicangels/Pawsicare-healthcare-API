package com.example.pawsicare.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO {
    private Long id;
    private LocalDateTime dateAndStart;
    private LocalDateTime dateAndEnd;
    private ClientDTO client;
    private DoctorDTO doctor;
    private PetDTO pet;
}
