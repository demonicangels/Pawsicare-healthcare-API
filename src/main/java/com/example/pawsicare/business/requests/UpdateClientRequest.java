package com.example.pawsicare.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientRequest {
    @Size(min = 3)
    @NotBlank
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String phoneNumber;
}
