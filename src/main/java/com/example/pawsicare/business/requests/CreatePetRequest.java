package com.example.pawsicare.business.requests;

import com.example.pawsicare.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePetRequest {
    private String token;
    private long ownerId;
    @Size(min = 3)
    @NotBlank
    private String name;
    private Gender gender;
    @NotBlank
    private String type;
    @NonNull
    private String birthday;
    private String information;
}
