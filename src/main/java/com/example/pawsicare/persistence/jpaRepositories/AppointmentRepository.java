package com.example.pawsicare.persistence.jpaRepositories;

import com.example.pawsicare.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long>{

    List<AppointmentEntity> findAppointmentEntitiesByClient_IdOrDoctor_Id(Long clientId, Long doctorId);

    void deleteById(long appId);
}
