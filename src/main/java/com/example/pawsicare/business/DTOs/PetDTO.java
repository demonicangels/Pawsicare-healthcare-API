package com.example.pawsicare.business.DTOs;

import com.example.pawsicare.domain.Gender;
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
    private Gender gender;
    @NotBlank
    private String type;
    @NotBlank
    private String birthday;
    private Integer age;
    @NotBlank
    private String information;
}
