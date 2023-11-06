package com.example.PawsiCare.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
    @Table(name = "pawsicare_users")
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.INTEGER)
    @NoArgsConstructor
    @SuperBuilder
    @Getter
    @Setter
    public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String birthday;
        private String email;
        private String password;
        private String phoneNumber;

        @Column(name = "role", insertable = false, updatable = false)
        private Integer role;

    }
