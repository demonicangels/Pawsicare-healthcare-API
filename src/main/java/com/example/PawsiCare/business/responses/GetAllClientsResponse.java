package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.domain.Client;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class GetAllClientsResponse {
    private List<Client> clients;
}
