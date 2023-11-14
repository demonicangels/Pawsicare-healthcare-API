package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.DoctorDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class getDoctorResponse {
    private DoctorDTO doctor;
}
