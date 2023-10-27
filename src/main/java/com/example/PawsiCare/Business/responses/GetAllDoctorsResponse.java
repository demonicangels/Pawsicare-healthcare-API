package com.example.PawsiCare.Business.responses;

import com.example.PawsiCare.Domain.Doctor;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class GetAllDoctorsResponse {
    private List<Doctor> doctors;
}
