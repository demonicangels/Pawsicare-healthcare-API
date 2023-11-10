package com.example.pawsicare.persistence.jpaRepositories;

import com.example.pawsicare.persistence.entity.appointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface appointmentRepository extends JpaRepository<appointmentEntity,Long>{

    List<appointmentEntity> findAppointmentEntitiesByClient_IdOrDoctor_Id(Long clientId, Long doctorId);;

    void deleteById(long appId);
}
