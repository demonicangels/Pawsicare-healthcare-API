package com.example.pawsicare.business.impl;

import com.example.pawsicare.persistence.entity.appointmentEntity;
import com.example.pawsicare.persistence.entity.clientEntity;
import com.example.pawsicare.persistence.entity.doctorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class appointmentRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    com.example.pawsicare.persistence.jpaRepositories.appointmentRepository appointmentRepository;


    @Test
    void save_shouldSaveAppointmentWithAllFields(){
      clientEntity client = clientEntity.builder()
              .name("Maria")
              .email("maria@gmail.com")
              .password("123")
              .build();

      entityManager.persist(client);

      doctorEntity doctor = doctorEntity.builder()
              .name("Maria")
              .email("maria@gmail.com")
              .password("123")
              .field("neurology")
              .build();

      entityManager.persist(doctor);


        appointmentEntity appointment = appointmentEntity.builder()
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(client)
                .doctor(doctor)
                .build();

        appointmentEntity savedAppointment = appointmentRepository.save(appointment);

        assertNotNull(savedAppointment.getId());

        savedAppointment = entityManager.find(appointmentEntity.class,savedAppointment.getId());

        appointmentEntity expectedAppointment = appointmentEntity.builder()
                .id(savedAppointment.getId())
                .dateAndStart(savedAppointment.getDateAndStart())
                .dateAndEnd(savedAppointment.getDateAndEnd())
                .client(savedAppointment.getClient())
                .doctor(savedAppointment.getDoctor())
                .build();

        assertEquals(savedAppointment,expectedAppointment);
    }

    @Test
    void find_findAllAppointmentsOfADoctor(){

        doctorEntity doctor = doctorEntity.builder()
                .name("Maria")
                .email("maria@gmail.com")
                .password("123")
                .field("neurology")
                .build();

        entityManager.persist(doctor);

        Long docId = doctor.getId();

        doctorEntity doctor2 = doctorEntity.builder()
                .name("Maria")
                .email("maria@gmail.com")
                .password("123")
                .field("neurology")
                .build();

        entityManager.persist(doctor2);

        appointmentEntity appointment = appointmentEntity.builder()
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(doctor)
                .build();

        entityManager.persist(appointment);

        appointmentEntity appointment2 = appointmentEntity.builder()
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(doctor)
                .build();

        entityManager.persist(appointment2);

        appointmentEntity appointment3 = appointmentEntity.builder()
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(doctor2)
                .build();

        entityManager.persist(appointment3);

        appointmentEntity appointment4 = appointmentEntity.builder()
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        entityManager.persist(appointment4);

        String jpql = "SELECT a FROM appointmentEntity a WHERE a.doctor.id = :doctorId";

        Query query = entityManager.createQuery(jpql, appointmentEntity.class);

        query.setParameter("doctorId", docId);

        List<appointmentEntity> doctorsAppointments = query.getResultList();

        assertNotNull(doctorsAppointments);
        assertThat(doctorsAppointments).hasSize(2);
    }
}
