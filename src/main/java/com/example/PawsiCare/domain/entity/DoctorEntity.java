package com.example.PawsiCare.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("1")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorEntity extends UserEntity {
    private String description;
    private String field;
    private String image;

    @ManyToMany(mappedBy = "doctors")
    private List<ClientEntity> clients;
}
