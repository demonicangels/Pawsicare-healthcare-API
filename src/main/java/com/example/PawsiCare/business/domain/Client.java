package com.example.PawsiCare.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    private long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    //private List<Pet> petList;


}
