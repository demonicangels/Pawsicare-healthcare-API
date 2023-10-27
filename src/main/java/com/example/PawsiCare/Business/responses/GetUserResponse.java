package com.example.PawsiCare.Business.responses;

import com.example.PawsiCare.Business.DTOs.ClientDTO;
import com.example.PawsiCare.Business.DTOs.DoctorDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class GetUserResponse {
    private Optional<DoctorDTO> loggedInDoctor;
    private Optional<ClientDTO> loggedInClient;
}
