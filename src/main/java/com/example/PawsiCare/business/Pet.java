package com.example.PawsiCare.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
