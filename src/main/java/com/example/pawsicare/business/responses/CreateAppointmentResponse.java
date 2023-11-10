package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.AppointmentDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAppointmentResponse {
    AppointmentDTO appointment;
}
