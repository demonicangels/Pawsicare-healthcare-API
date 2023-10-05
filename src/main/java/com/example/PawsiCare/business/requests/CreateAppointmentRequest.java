package com.example.PawsiCare.business.requests;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest {
    @NonNull
    private Date dateAndTime;
    @NonNull
    private long clientId;
    @NonNull
    private long doctorId;
    @NonNull
    private long petId;
}
