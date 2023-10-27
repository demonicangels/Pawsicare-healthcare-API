package com.example.PawsiCare.Domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Doctor extends User {
    private long id;
    //private String medicalCode;
    private Integer age;
    private String description;
    private String field;
    private String image;
    //private double rating;

}
