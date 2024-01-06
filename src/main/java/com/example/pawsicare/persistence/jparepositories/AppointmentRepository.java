package com.example.pawsicare.persistence.jparepositories;

import com.example.pawsicare.persistence.entity.AppointmentEntity;
import com.example.pawsicare.persistence.entity.ClientEntity;
import com.example.pawsicare.persistence.entity.DoctorEntity;
import com.example.pawsicare.persistence.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long>{

    List<AppointmentEntity> findAppointmentEntitiesByClient_IdOrDoctor_Id(Long clientId, Long doctorId);

    @Modifying
    @Query("update AppointmentEntity a set a.client = :client, a.pet = :pet where a.dateAndStart = :dateAndStart and a.doctor = :doctor")
    void updateAppointmentEntityByDateAndAndDateAndStartAndDoctor(@Param("dateAndStart")LocalDateTime dateAndStart, @Param("doctor") DoctorEntity doctor, @Param("client") ClientEntity client, @Param("pet") PetEntity pet);

    @Query("select a from AppointmentEntity a where a.doctor.id = :docId and a.client = null")
    List<AppointmentEntity> getDocSchedule(@Param("docId") Long docId);
    @Modifying
    void deleteById(long appId);
}
