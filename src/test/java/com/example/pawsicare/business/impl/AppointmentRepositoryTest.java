package com.example.pawsicare.business.impl;

import com.example.pawsicare.persistence.entity.AppointmentEntity;
import com.example.pawsicare.persistence.entity.ClientEntity;
import com.example.pawsicare.persistence.entity.DoctorEntity;
import com.example.pawsicare.persistence.jparepositories.AppointmentRepository;
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
class AppointmentRepositoryTest {

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
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(client)
                .doctor(doctor)
                .build();

        AppointmentEntity savedAppointment = appointmentRepository.save(appointment);

        assertNotNull(savedAppointment.getId());

        savedAppointment = entityManager.find(AppointmentEntity.class,savedAppointment.getId());

        AppointmentEntity expectedAppointment = AppointmentEntity.builder()
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
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(doctor)
                .build();

        entityManager.persist(appointment);

        AppointmentEntity appointment2 = AppointmentEntity.builder()
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(doctor)
                .build();

        entityManager.persist(appointment2);

        AppointmentEntity appointment3 = AppointmentEntity.builder()
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(doctor2)
                .build();

        entityManager.persist(appointment3);

        AppointmentEntity appointment4 = AppointmentEntity.builder()
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
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

    @Test
    void update_rescheduleAnAppointment(){

        DoctorEntity doctor = DoctorEntity.builder()
                .name("Maria")
                .email("maria@gmail.com")
                .password("123")
                .field("neurology")
                .build();

        entityManager.persist(doctor);

        DoctorEntity doctor2 = DoctorEntity.builder()
                .name("Maria")
                .email("maria@gmail.com")
                .password("123")
                .field("neurology")
                .build();

        entityManager.persist(doctor2);

        AppointmentEntity updatedAppointment = AppointmentEntity.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(doctor2)
                .build();

        updatedAppointment = entityManager.merge(updatedAppointment);
        appointmentRepository.save(updatedAppointment);

        AppointmentEntity dbUpdatedAppointment = appointmentRepository.findById(1L).get();

        assertNotNull(dbUpdatedAppointment);
        assertEquals(1L,dbUpdatedAppointment.getId());
        assertEquals(dbUpdatedAppointment.getDoctor(), doctor2);
    }


}
