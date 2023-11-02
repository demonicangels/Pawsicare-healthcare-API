package com.example.PawsiCare.domain.jpaRepositories;

import com.example.PawsiCare.domain.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long>{
    List<AppointmentEntity> findAppointmentEntitiesByDoctor_Id(Long doctorId);
}
