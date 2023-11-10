package com.example.pawsicare.persistence.jpaRepositories;

import com.example.pawsicare.persistence.entity.doctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface doctorRepository extends JpaRepository<doctorEntity,Long> {
    Optional<doctorEntity> getDoctorEntitiesByField(String field);
}
