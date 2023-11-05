package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.DTOs.ClientDTO;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class GetAllClientsResponse {
    private List<ClientDTO> clients;
}
