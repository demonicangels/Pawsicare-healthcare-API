package com.example.PawsiCare.domain.jpaRepositories;

import com.example.PawsiCare.domain.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity,Long> {
}
