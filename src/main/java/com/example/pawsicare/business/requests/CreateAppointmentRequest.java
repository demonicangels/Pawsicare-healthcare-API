package com.example.pawsicare.business.requests;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest {
    private String date;
    private String start;
    private String end;
    private long clientId;
    private long doctorId;
    private long petId;
}
