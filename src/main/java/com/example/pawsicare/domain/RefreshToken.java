package com.example.pawsicare.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshToken{
    @NotEmpty
    String token;
    @NotNull
    Date expiryDate;
    User userInfo;
}