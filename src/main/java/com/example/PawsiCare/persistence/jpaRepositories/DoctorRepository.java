package com.example.PawsiCare.persistence.jpaRepositories;

import com.example.PawsiCare.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity,Long> {
    Optional<DoctorEntity> getDoctorEntitiesByField(String field);
}
