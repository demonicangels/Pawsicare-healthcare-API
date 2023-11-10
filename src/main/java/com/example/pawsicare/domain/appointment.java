package com.example.pawsicare.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class appointment {
    private long id;
    private LocalDateTime dateAndStart;
    private LocalDateTime dateAndEnd;
    private com.example.pawsicare.domain.client client;
    private com.example.pawsicare.domain.doctor doctor;
    private com.example.pawsicare.domain.pet pet;
}
