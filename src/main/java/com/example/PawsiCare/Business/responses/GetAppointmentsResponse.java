package com.example.PawsiCare.Business.responses;

import com.example.PawsiCare.Domain.Appointment;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class GetAppointmentsResponse {
    List<Appointment> appointments;
}
