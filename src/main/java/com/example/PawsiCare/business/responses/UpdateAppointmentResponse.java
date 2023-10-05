package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.domain.Appointment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAppointmentResponse {
    Appointment updatedAppointment;
}
