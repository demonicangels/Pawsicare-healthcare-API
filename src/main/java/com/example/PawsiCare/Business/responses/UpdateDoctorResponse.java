package com.example.PawsiCare.Business.responses;

import com.example.PawsiCare.Domain.Doctor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDoctorResponse {
    private Doctor updatedDoctor;
}
