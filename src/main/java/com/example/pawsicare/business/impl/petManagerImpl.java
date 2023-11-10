package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.pet;
import com.example.pawsicare.domain.managerinterfaces.petManager;
import com.example.pawsicare.persistence.petEntityConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class petManagerImpl implements petManager {

    private final com.example.pawsicare.persistence.jpaRepositories.petRepository petRepository;
    private final petEntityConverter converter;

    @Override
    public pet createPet(pet pet) {
        return converter.fromEntity(petRepository.save(converter.toEntity(pet)));
    }

    @Override
    public pet updatePet(long id, pet pet) {
       return converter.fromEntity(petRepository.save(converter.toEntity(pet)));
    }

    @Override
    public pet getPet(long id) {
        return converter.fromEntity(petRepository.getPetEntityById(id));
    }

    /**
     * @param ownerId
     * @return pets when asked for pets
     * @should return a response with list containing all pets with the specified ownerId when such pets are present
     * @should return a response with an empty list when there are no pets with the specified ownerId present
     */
    @Override
    public List<pet> getPets(long ownerId) {
        List<pet> pets = new ArrayList<>();
        petRepository.getPetEntitiesByClient_Id(ownerId).stream().map(
                converter :: fromEntity
        ).toList().forEach(
                pets :: add
        );

        return pets;
    }

    @Override
    public void deletePet(long id) {
        petRepository.deleteById(id);
    }

}
