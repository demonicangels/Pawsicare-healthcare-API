package com.example.PawsiCare.Business.DTOs;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDTO {
    private String name;
    private String birthday;
    private Integer age;
    private String description;
    private String email;
    private String phoneNumber;
}
