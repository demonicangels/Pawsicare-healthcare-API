package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.Pet;
import com.example.pawsicare.domain.managerinterfaces.PetManager;
import com.example.pawsicare.persistence.PetEntityConverter;
import com.example.pawsicare.persistence.jpaRepositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class PetManagerImpl implements PetManager {

    private final PetRepository petRepository;
    private final PetEntityConverter converter;

    @Override
    public Pet createPet(Pet pet) {
        return converter.fromEntity(petRepository.save(converter.toEntity(pet)));
    }

    @Override
    public Pet updatePet(long id, Pet pet) {
       return converter.fromEntity(petRepository.save(converter.toEntity(pet)));
    }

    @Override
    public Pet getPet(long id) {
        return converter.fromEntity(petRepository.getPetEntityById(id));
    }

    /**
     * @param ownerId
     * @return pets when asked for pets
     * @should return a response with list containing all pets with the specified ownerId when such pets are present
     * @should return a response with an empty list when there are no pets with the specified ownerId present
     */
    @Override
    public List<Pet> getPets(long ownerId) {
        List<Pet> pets = new ArrayList<>();
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
