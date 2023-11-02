package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.domain.entity.AppointmentEntity;
import com.example.PawsiCare.domain.entity.ClientEntity;
import com.example.PawsiCare.domain.entity.DoctorEntity;
import com.example.PawsiCare.domain.jpaRepositories.AppointmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppointmentRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    AppointmentRepository appointmentRepository;


    @Test
    void save_shouldSaveAppointmentWithAllFields(){
      ClientEntity client = ClientEntity.builder()
              .name("Maria")
              .email("maria@gmail.com")
              .password("123")
              .build();

      entityManager.persist(client);

      DoctorEntity doctor = DoctorEntity.builder()
              .name("Maria")
              .email("maria@gmail.com")
              .password("123")
              .field("neurology")
              .build();

      entityManager.persist(doctor);


        AppointmentEntity appointment = AppointmentEntity.builder()
                .date(LocalDate.now())
                .time(12)
                .client(client)
                .doctor(doctor)
                .build();

        AppointmentEntity savedAppointment = appointmentRepository.save(appointment);

        assertNotNull(savedAppointment.getId());

        savedAppointment = entityManager.find(AppointmentEntity.class,savedAppointment.getId());

        AppointmentEntity expectedAppointment = AppointmentEntity.builder()
                .id(savedAppointment.getId())
                .date(savedAppointment.getDate())
                .time(savedAppointment.getTime())
                .client(savedAppointment.getClient())
                .doctor(savedAppointment.getDoctor())
                .build();

        assertEquals(savedAppointment,expectedAppointment);
    }

    @Test
    void find_findAllAppointmentsOfADoctor(){

        DoctorEntity doctor = DoctorEntity.builder()
                .name("Maria")
                .email("maria@gmail.com")
                .password("123")
                .field("neurology")
                .build();

        entityManager.persist(doctor);

        Long docId = doctor.getId();

        DoctorEntity doctor2 = DoctorEntity.builder()
                .name("Maria")
                .email("maria@gmail.com")
                .password("123")
                .field("neurology")
                .build();

        entityManager.persist(doctor2);

        AppointmentEntity appointment = AppointmentEntity.builder()
                .date(LocalDate.now())
                .time(12)
                .client(null)
                .doctor(doctor)
                .build();

        entityManager.persist(appointment);

        AppointmentEntity appointment2 = AppointmentEntity.builder()
                .date(LocalDate.now())
                .time(12)
                .client(null)
                .doctor(doctor)
                .build();

        entityManager.persist(appointment2);

        AppointmentEntity appointment3 = AppointmentEntity.builder()
                .date(LocalDate.now())
                .time(12)
                .client(null)
                .doctor(doctor2)
                .build();

        entityManager.persist(appointment3);

        AppointmentEntity appointment4 = AppointmentEntity.builder()
                .date(LocalDate.now())
                .time(12)
                .client(null)
                .doctor(null)
                .build();

        entityManager.persist(appointment4);

        String jpql = "SELECT a FROM AppointmentEntity a WHERE a.doctor.id = :doctorId";

        Query query = entityManager.createQuery(jpql, AppointmentEntity.class);

        query.setParameter("doctorId", docId);

        List<AppointmentEntity> doctorsAppointments = query.getResultList();

        assertNotNull(doctorsAppointments);
        assertThat(doctorsAppointments).hasSize(2);
    }
}
