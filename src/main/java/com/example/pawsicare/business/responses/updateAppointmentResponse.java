package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.appointmentDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class updateAppointmentResponse {
    appointmentDTO updatedAppointment;
}
