package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.clientDTO;
import com.example.pawsicare.business.DTOs.doctorDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class getUserResponse {
    private long id;
    private Optional<doctorDTO> loggedInDoctor;
    private Optional<clientDTO> loggedInClient;
}
