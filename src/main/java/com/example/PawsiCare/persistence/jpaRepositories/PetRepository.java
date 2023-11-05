package com.example.PawsiCare.persistence.jpaRepositories;

import com.example.PawsiCare.persistence.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity,Long> {
}
