package com.example.PawsiCare.business.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest {
    private Date dateAndTime;
    private long clientId;
    private long doctorId;
    private long petId;
}
