package com.example.PawsiCare.domain.jpaRepositories;

import com.example.PawsiCare.domain.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long>{
}
