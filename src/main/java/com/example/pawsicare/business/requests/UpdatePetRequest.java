package com.example.pawsicare.business.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePetRequest {

    private String token;
    private Long id;
    private Long ownerId;
    private String name;
    private String information;
}
