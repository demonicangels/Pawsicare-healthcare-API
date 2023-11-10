package com.example.pawsicare.business.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class updateAppointmentRequest {
    private LocalDateTime dateAndStart;
    private LocalDateTime dateAndEnd;
}
