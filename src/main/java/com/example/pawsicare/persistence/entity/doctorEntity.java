package com.example.pawsicare.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("1")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class doctorEntity extends userEntity {
    private String description;
    private String field;
    private String image;

    @ManyToMany(mappedBy = "doctors")
    private List<clientEntity> clients;
}
