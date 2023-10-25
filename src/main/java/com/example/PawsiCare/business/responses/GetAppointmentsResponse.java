package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.domain.Appointment;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class GetAppointmentsResponse {
    List<Appointment> appointments;
}