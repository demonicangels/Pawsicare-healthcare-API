package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.dto.ClientDTO;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class GetAllClientsResponse {
    private List<ClientDTO> clients;
}
