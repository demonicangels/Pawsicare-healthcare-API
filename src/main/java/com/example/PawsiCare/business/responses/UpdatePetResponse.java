package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.domain.Pet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePetResponse {
    private Pet updatedPet;
}
