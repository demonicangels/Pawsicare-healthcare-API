package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.domain.Doctor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDoctorResponse {
    private Doctor updatedDoctor;
}