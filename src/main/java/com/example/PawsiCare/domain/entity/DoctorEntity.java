package com.example.PawsiCare.domain.entity;

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
public class DoctorEntity extends UserEntity {
    private String description;
    private String field;
    private String image;

    @ManyToMany(mappedBy = "doctors")
    private List<ClientEntity> clients;
}
