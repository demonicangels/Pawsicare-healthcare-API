package com.example.PawsiCare.persistence.jpaRepositories;

import com.example.PawsiCare.persistence.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PetRepository extends JpaRepository<PetEntity,Long> {
    PetEntity getPetEntityById(long id);
    List<PetEntity> getPetEntitiesByClient_Id(long ownerId);
    void deleteById(long id);

}
