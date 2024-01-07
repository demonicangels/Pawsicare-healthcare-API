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

    //making an appointment based on chosen slot from the doctor's schedule
    @Modifying
    @Query("update AppointmentEntity a set a.client = :client, a.pet = :pet where a.dateAndStart = :dateAndStart and a.doctor = :doctor")
    void updateAppointmentEntityByDateAndAndDateAndStartAndDoctor(@Param("dateAndStart")LocalDateTime dateAndStart, @Param("doctor") DoctorEntity doctor, @Param("client") ClientEntity client, @Param("pet") PetEntity pet);

    //reschedule an appointment to a different slot in the doctor's schedule
    @Modifying
    @Query("update AppointmentEntity a set a.client = :client, a.pet = :pet where a.dateAndStart = :dateAndStart and a.dateAndEnd = :dateAndEnd and a.doctor = :doctor")
    void updateAppointmentEntityByDoctorAndClientAndPet(@Param("dateAndStart")LocalDateTime dateAndStart, @Param("dateAndEnd")LocalDateTime dateAndEnd, @Param("doctor")DoctorEntity doctor, @Param("client") ClientEntity client, @Param("pet") PetEntity pet);

    //empty previous taken slot after rescheduling to another
    @Modifying
    @Query("update AppointmentEntity a set a.client = null, a.pet = null where a.id = :id and a.doctor = :doctor")
    void updateAppointmentEntityByIdAndDoctorAndClientAndPet(@Param("id") Long id,
                           @Param("doctor") DoctorEntity doctor);
    @Query("select a from AppointmentEntity a where a.doctor.id = :docId and a.client = null")
    List<AppointmentEntity> getDocSchedule(@Param("docId") Long docId);

    @Modifying
    void deleteById(long appId);
}
