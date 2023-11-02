package com.example.PawsiCare.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "pets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name")
    @Length(min = 3, max = 20)
    private String name;

    @NotEmpty
    @Column(name = "birthday")
    private String birthday;

    @NotEmpty
    @Column(name = "information")
    private String information;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private ClientEntity client;
}
