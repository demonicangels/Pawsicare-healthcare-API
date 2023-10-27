package com.example.PawsiCare.Business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRequest {
    @Size(min = 3)
    @NotBlank
    private String name;
    @Size(min = 3)
    @NotBlank
    private String password;
    private String description;
    @NotBlank
    private String field;
    @NotBlank
    private String email;
    private String phoneNumber;
}
