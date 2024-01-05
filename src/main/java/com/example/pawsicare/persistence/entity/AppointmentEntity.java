package com.example.pawsicare.persistence.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "dateAndStartTime")
    private LocalDateTime dateAndStart;


    @NotNull
    @Column(name = "dateAndEndTime")
    private LocalDateTime dateAndEnd;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "client")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "doctor")
    private DoctorEntity doctor;

    @Nullable
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="pet")
    private PetEntity pet;

}
