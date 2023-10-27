package com.example.PawsiCare.Domain;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    private long id;
    private Date dateAndTime;
    private long clientId;
    private long doctorId;
    private long petId;
}
