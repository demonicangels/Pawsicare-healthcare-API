package com.example.pawsicare.persistence.jparepositories;

import com.example.pawsicare.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity,Long> {
    Optional<List<DoctorEntity>> getDoctorEntitiesByField(String field);
}
