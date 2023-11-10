package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.AppointmentDTO;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class GetAppointmentsResponse {
    List<AppointmentDTO> appointments;
}
