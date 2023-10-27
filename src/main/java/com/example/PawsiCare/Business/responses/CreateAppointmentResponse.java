package com.example.PawsiCare.Business.responses;

import com.example.PawsiCare.Domain.Appointment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAppointmentResponse {
    Appointment appointment;
}
