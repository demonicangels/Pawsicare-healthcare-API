package com.example.PawsiCare.Business.responses;

import com.example.PawsiCare.Domain.Client;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class GetAllClientsResponse {
    private List<Client> clients;
}
