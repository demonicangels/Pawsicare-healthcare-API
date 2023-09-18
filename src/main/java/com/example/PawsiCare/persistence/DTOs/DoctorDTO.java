package com.example.PawsiCare.persistence.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {
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
