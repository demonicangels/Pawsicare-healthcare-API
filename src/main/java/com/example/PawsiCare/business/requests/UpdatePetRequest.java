package com.example.PawsiCare.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UpdatePetRequest {

    private long ownerId;
    @Size(min = 3)
    @NotBlank
    private String name;
    private String information;
}
