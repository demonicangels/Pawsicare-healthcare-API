package com.example.PawsiCare.persistence.jpaRepositories;

import com.example.PawsiCare.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long>{
    List<AppointmentEntity> findAppointmentEntitiesByDoctor_Id(Long doctorId);
}
