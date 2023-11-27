package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.dto.ClientDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateClientResponse {
    private ClientDTO updatedClient;
}
