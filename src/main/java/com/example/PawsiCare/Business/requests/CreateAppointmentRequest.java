package com.example.PawsiCare.Business.requests;

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
