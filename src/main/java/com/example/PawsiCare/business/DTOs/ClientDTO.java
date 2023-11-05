package com.example.PawsiCare.business.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDTO {
    private Long id;
    @NotBlank
    @Size(min = 3)
    private String name;
    private String birthday;
    @NotBlank
    @Size(min = 3)
    private String password;
    @NotBlank
    private String email;
    private String phoneNumber;
}
