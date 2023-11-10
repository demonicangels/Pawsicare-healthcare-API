package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.petDTO;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class getAllPetsResponse {
    List<petDTO> pets;
}
