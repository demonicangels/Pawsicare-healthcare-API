package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.domain.Client;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetClientResponse {
    private Client client;
}