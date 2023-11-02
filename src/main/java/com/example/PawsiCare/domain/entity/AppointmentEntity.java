package com.example.PawsiCare.domain.entity;

import com.example.PawsiCare.domain.Client;
import com.example.PawsiCare.domain.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

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
    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "time")
    private Integer time;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;








}
