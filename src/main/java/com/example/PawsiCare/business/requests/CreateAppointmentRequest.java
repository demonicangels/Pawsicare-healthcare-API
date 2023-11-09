package com.example.PawsiCare.business.requests;

import com.example.PawsiCare.business.DTOs.ClientDTO;
import com.example.PawsiCare.business.DTOs.DoctorDTO;
import com.example.PawsiCare.business.DTOs.PetDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest {
    private LocalDateTime dateAndStart;
    private LocalDateTime dateAndEnd;
    private long clientId;
    private long doctorId;
    private long petId;
}
