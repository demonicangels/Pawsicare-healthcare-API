package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.domain.Doctor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDoctorResponse {
    //private long id;
    Doctor doctor;
}
