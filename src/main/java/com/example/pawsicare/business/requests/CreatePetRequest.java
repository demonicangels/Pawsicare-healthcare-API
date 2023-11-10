package com.example.pawsicare.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePetRequest {
    private long ownerId;
    @Size(min = 3)
    @NotBlank
    private String name;
    @NonNull
    private String birthday;
    private Integer age;
    private String information;
}
