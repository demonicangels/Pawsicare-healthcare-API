package com.example.PawsiCare.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("0")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity extends UserEntity{

    @OneToMany(mappedBy = "client")
    private List<PetEntity> pets;

    @ManyToMany
    @JoinTable(
            name = "client_doctor",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<DoctorEntity> doctors;


}
