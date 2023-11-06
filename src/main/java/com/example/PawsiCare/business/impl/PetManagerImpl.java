package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.domain.Pet;
import com.example.PawsiCare.domain.managerInterfaces.PetManager;
import com.example.PawsiCare.persistence.PetEntityConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.PawsiCare.persistence.jpaRepositories.PetRepository;

import java.util.*;


@Service
@AllArgsConstructor
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
