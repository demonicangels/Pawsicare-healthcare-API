package com.example.pawsicare.business.DTOs;

import com.example.pawsicare.domain.Role;
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
    private Role role;
}
