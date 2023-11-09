package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.DTOs.DoctorDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDoctorResponse {
    private DoctorDTO updatedDoctor;
}
