package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.dto.DoctorDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetDoctorResponse {
    private DoctorDTO doctor;
}
