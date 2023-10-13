package com.example.PawsiCare.business.DTOs;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorDTO {
    private String name;
    private String birthday;
    private Integer age;
    private String description;
    private String field;
    private String email;
    private String phoneNumber;
}
