package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.domain.Pet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePetResponse {
    private Pet pet;
}
