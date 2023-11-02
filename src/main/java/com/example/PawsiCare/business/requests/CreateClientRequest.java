package com.example.PawsiCare.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientRequest {
    @Size(min = 3)
    @NotBlank
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String phoneNumber;
}
