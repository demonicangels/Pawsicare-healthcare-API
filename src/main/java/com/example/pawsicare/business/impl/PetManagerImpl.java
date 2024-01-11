package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.Pet;
import com.example.pawsicare.domain.managerinterfaces.PetManager;
import com.example.pawsicare.persistence.converters.PetEntityConverter;
import com.example.pawsicare.persistence.entity.PetEntity;
import com.example.pawsicare.persistence.jparepositories.PetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class PetManagerImpl implements PetManager {

    private final PetRepository petRepository;
    private final PetEntityConverter converter;

    /**
     * @param pet
     * @return pet object when pet is created
     * @should create pet object with all fields when created
     */
    @Override
    public Pet createPet(Pet pet) {
        return converter.fromEntity(petRepository.save(converter.toEntity(pet)));
    }

    /**
     * @param pet
     * @return updated pet object
     * @should verify the repository method is called correctly
     * @should set the values of the variables from the object from the db when their values are null or empty
     *
     */
    @Transactional
    @Override
    public void updatePet(Pet pet) {

        PetEntity peti = petRepository.getPetEntityById(pet.getId());

        if (pet.getName() == null || pet.getName().isEmpty()) {
            pet.setName(peti.getName());
        }

        if (pet.getInformation() == null || pet.getInformation().isEmpty()) {
            pet.setInformation(peti.getInformation());
        }
        petRepository.updatePetEntityById(pet.getId(),pet.getName(),pet.getInformation());

    }

    /**
     * @param id
     * @should return a pet when the id matches
     * @return pet when found
     */
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

    /**
     * @param id
     * @should verify if the deleteById method of the repository is being called
     */
    @Override
    public void deletePet(long id) {
        petRepository.deleteById(id);
    }

}
