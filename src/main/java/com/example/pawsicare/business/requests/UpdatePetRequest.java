package com.example.pawsicare.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePetRequest {

    private Long id;
    private Long ownerId;
    private String name;
    private String information;
}
