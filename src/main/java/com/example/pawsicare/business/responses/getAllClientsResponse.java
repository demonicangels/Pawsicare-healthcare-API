package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.ClientDTO;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class getAllClientsResponse {
    private List<ClientDTO> clients;
}
