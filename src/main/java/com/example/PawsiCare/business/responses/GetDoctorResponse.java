package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.DTOs.DoctorDTO;
import com.example.PawsiCare.domain.Doctor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetDoctorResponse {
    private DoctorDTO doctor;
}
