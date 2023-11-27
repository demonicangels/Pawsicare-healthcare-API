package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.dto.DoctorDTO;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class GetAllDoctorsResponse {
    private List<DoctorDTO> doctors;
}
