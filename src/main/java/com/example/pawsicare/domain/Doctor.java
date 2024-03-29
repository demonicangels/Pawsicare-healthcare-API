package com.example.pawsicare.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Doctor extends User {
    //private String medicalCode;
    private Integer age;
    private String description;
    private String field;
    private String image;
    //private double rating;

}
