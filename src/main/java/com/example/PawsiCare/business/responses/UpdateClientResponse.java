package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.DTOs.ClientDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateClientResponse {
    private ClientDTO updatedClient;
}
