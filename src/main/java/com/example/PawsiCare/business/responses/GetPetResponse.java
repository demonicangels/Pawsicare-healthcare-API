package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.DTOs.PetDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPetResponse {
    private PetDTO pet;
}
