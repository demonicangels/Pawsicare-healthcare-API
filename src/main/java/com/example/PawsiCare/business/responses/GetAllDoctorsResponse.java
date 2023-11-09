package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.DTOs.DoctorDTO;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class GetAllDoctorsResponse {
    private List<DoctorDTO> doctors;
}
