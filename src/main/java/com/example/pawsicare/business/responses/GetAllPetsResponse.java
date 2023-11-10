package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.PetDTO;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class GetAllPetsResponse {
    List<PetDTO> pets;
}
