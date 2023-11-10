package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.clientDTO;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class getAllClientsResponse {
    private List<clientDTO> clients;
}
