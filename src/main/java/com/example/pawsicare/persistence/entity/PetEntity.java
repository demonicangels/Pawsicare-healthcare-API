package com.example.pawsicare.persistence.entity;

import com.example.pawsicare.domain.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    @Length(max = 6)
    private Gender gender;

    @NotEmpty
    @Column(name="type_of_animal")
    @Length(max = 8)
    private String type;

    @NotEmpty
    @Column(name = "birthday")
    private String birthday;

    @NotEmpty
    @Column(name = "information")
    private String information;

    @ManyToOne
    @JoinColumn(name = "owner")
    private ClientEntity client;
}
