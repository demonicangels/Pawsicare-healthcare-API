package com.example.pawsicare.business.responses;

import com.example.pawsicare.business.DTOs.petDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class createPetResponse {
    private petDTO pet;
}
