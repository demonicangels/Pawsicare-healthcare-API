package com.example.PawsiCare.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    private long id;
    //private String medicalCode;
    private String name;
    private Integer age;
    private String password;
    private String description;
    private String field;
    private String email;
    private String phoneNumber;
    //private double rating;

}
