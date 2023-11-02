package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.domain.Appointment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAppointmentResponse {
    Appointment appointment;
}
