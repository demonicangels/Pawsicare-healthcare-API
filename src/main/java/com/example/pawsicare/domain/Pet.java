package com.example.pawsicare.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {

    private long id;
    private long ownerId;
    private String name;
    private Gender gender;
    private String type;
    private String birthday;
    private Integer age;
    private String information;
}
