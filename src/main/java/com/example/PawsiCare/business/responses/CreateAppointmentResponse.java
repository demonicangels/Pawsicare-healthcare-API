package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.DTOs.AppointmentDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAppointmentResponse {
    AppointmentDTO appointment;
}
