package com.example.PawsiCare.business.DTOs;

import com.example.PawsiCare.domain.Client;
import com.example.PawsiCare.domain.Doctor;
import com.example.PawsiCare.domain.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
