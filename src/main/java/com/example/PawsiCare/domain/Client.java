package com.example.PawsiCare.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Client extends User {
    private long id;

    //private List<Pet> petList;


}
