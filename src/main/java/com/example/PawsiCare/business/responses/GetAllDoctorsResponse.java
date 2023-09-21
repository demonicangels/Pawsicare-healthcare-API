package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.domain.Doctor;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class GetAllDoctorsResponse {
    private List<Doctor> doctors;
}
