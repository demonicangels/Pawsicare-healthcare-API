package com.example.pawsicare.persistence.jparepositories;

import com.example.pawsicare.persistence.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface PetRepository extends JpaRepository<PetEntity,Long> {
    PetEntity getPetEntityById(long id);
    List<PetEntity> getPetEntitiesByClient_Id(long ownerId);


    @Modifying
    @Query("update PetEntity p set p.name = coalesce(:name, p.name), p.information = coalesce(:info, p.information) where p.id = :id")
    void updatePetEntityById(@Param("id") Long id, @Param("name") String name, @Param("info") String info);

    void deleteById(long id);

}
