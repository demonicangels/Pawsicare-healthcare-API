package com.example.PawsiCare.Business.responses;

import com.example.PawsiCare.Domain.Pet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePetResponse {
    private Pet pet;
}
