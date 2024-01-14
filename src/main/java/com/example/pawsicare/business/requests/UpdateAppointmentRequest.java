package com.example.pawsicare.business.requests;

import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.business.dto.PetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentRequest {
    private String token;
    private Long id;
    private LocalDateTime dateAndStart;
    private LocalDateTime dateAndEnd;
    private DoctorDTO doctor;
    private ClientDTO client;
    private PetDTO pet;

}
