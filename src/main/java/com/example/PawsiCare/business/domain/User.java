package com.example.PawsiCare.business.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role", defaultImpl = Client.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Doctor.class, name = "Doctor"),
        @JsonSubTypes.Type(value = Client.class, name = "Client")
})
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class User {
    private String name;
    private String birthday;
    private String password;
    private String email;
    private String phoneNumber;
    private Role role;
}
