package com.example.pawsicare.persistence.jpaRepositories;

import com.example.pawsicare.persistence.entity.petEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface petRepository extends JpaRepository<petEntity,Long> {
    petEntity getPetEntityById(long id);

    //TODO swap getPetById with this method and test it PetEntity getPetEntityByNameAndClient(long id, long ownerId);
    List<petEntity> getPetEntitiesByClient_Id(long ownerId);

    void deleteById(long id);

}
