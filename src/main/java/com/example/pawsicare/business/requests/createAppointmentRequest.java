package com.example.pawsicare.business.requests;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class createAppointmentRequest {
    private LocalDateTime dateAndStart;
    private LocalDateTime dateAndEnd;
    private long clientId;
    private long doctorId;
    private long petId;
}
