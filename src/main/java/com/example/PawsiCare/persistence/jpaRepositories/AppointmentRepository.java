package com.example.PawsiCare.persistence.jpaRepositories;

import com.example.PawsiCare.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long>{

    List<AppointmentEntity> findAppointmentEntitiesByClient_IdOrDoctor_Id(Long clientId, Long doctorId);;

    void deleteById(long appId);
}
