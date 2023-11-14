package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.ClientDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class getClientResponse {
    private ClientDTO client;
}
