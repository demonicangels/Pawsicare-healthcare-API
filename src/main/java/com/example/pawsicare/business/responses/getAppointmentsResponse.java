package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.appointmentDTO;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class getAppointmentsResponse {
    List<appointmentDTO> appointments;
}
