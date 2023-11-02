package com.example.PawsiCare.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pawsicare_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.INTEGER)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String birthday;
    private String email;
    private String password;
    private String phoneNumber;
}
