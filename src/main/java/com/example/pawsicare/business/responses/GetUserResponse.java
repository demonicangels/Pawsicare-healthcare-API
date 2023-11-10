package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.ClientDTO;
import com.example.pawsicare.business.DTOs.DoctorDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class GetUserResponse {
    private long id;
    private Optional<DoctorDTO> loggedInDoctor;
    private Optional<ClientDTO> loggedInClient;
}
