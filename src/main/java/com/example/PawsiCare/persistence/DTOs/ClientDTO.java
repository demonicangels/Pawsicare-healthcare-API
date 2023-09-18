package com.example.PawsiCare.persistence.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {
    private long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    //private List<Pet> petList;


}
