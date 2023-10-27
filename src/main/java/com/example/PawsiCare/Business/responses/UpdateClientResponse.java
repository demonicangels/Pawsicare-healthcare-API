package com.example.PawsiCare.Business.responses;

import com.example.PawsiCare.Domain.Client;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateClientResponse {
    private Client updatedClient;
}
