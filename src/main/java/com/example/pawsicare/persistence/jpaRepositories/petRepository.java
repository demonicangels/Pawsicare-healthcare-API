package com.example.pawsicare.persistence.jpaRepositories;

import com.example.pawsicare.persistence.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface petRepository extends JpaRepository<PetEntity,Long> {
    PetEntity getPetEntityById(long id);

    //TODO swap getPetById with this method and test it PetEntity getPetEntityByNameAndClient(long id, long ownerId);
    List<PetEntity> getPetEntitiesByClient_Id(long ownerId);

    void deleteById(long id);

}
