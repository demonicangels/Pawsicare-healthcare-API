package com.example.pawsicare.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role", defaultImpl = client.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = doctor.class, name = "Doctor"),
        @JsonSubTypes.Type(value = client.class, name = "Client")
})
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class user {
    private Long id;
    private String name;
    private String birthday;
    private String password;
    private String email;
    private String phoneNumber;
    private com.example.pawsicare.domain.role role;
}
