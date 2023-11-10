package com.example.pawsicare.business.DTOs;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class doctorDTO {
    private Long id;
    private String name;
    private String birthday;
    private Integer age;
    private String description;
    private String password;
    private String field;
    private String email;
    private String phoneNumber;
    private String image;
}
