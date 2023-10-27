package com.example.PawsiCare.Domain;

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
    private String birthday;
    private Integer age;
    private String information;
}
