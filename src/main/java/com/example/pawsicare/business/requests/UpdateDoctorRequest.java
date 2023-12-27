package com.example.pawsicare.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDoctorRequest {
    String token;
    Long id;
    @Size(min = 3)
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    private String field;
    private String description;
    @NotBlank
    private String email;
    private String phoneNumber;
}
