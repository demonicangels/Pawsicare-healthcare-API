package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.dto.PetDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPetResponse {
    private PetDTO pet;
}
