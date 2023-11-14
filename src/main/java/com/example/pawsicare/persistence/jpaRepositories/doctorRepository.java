package com.example.pawsicare.persistence.jpaRepositories;

import com.example.pawsicare.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface doctorRepository extends JpaRepository<DoctorEntity,Long> {
    Optional<DoctorEntity> getDoctorEntitiesByField(String field);
}
