package com.example.PawsiCare.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Client extends User {
}
