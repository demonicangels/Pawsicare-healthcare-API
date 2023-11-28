package com.example.pawsicare.domain;

import com.example.pawsicare.persistence.entity.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshToken{
    @NotNull
    Long id;
    @NotEmpty
    String token;
    @NotNull
    Instant expiryDate;
    User userInfo;
}