package com.example.PawsiCare.business.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDTO {

    private long id;
    @NotNull
    private long ownerId;
    @NotBlank
    private String name;
    @NotBlank
    private String birthday;
    private Integer age;
    @NotBlank
    private String information;
}
