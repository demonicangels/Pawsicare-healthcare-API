package com.example.PawsiCare.business.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentRequest {
    private LocalDate date;
    private int time;
}
