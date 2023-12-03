package com.example.pawsicare.business.dto;

import com.example.pawsicare.domain.Role;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorDTO {
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
    private Role role;
}
