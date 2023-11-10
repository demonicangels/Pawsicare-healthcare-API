package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.doctorDTO;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class getAllDoctorsResponse {
    private List<doctorDTO> doctors;
}
