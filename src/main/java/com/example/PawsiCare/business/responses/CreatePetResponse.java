package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.domain.Pet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePetResponse {
//    private long id;
//    private long ownerId;
    private Pet pet;
}
