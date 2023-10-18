package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.domain.Pet;
import com.example.PawsiCare.domain.managerInterfaces.PetManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.PawsiCare.domain.repositoryInterfaces.PetRepository;

import java.util.*;


@Service
@AllArgsConstructor
public class PetManagerImpl implements PetManager {

    private final PetRepository petRepository;

    @Override
    public Pet createPet(Pet pet) {
        return petRepository.createPet(pet);
    }

    @Override
    public Pet updatePet(long id, Pet pet) {
       return petRepository.updatePet(id, pet);
    }

    @Override
    public Pet getPet(long id) {
        return petRepository.getPet(id);
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
        for(Pet p : petRepository.getPets(ownerId)){

            pets.add(p);

        }
        return pets;
    }

    @Override
    public void deletePet(long id) {
        petRepository.deletePet(id);
    }

//    private PetDTO ToDTO(Pet pet){
//        PetDTO p = new PetDTO();
//        p.setId(pet.getId());
//        p.setName(pet.getName());
//        p.setAge(pet.getAge());
//        p.setBirthday(pet.getBirthday());
//        p.setInformation(pet.getInformation());
//        p.setOwnerId(pet.getOwnerId());
//        return p;
//    }

//    private Pet FromDTO(PetDTO pet){
//        Pet p = new Pet();
//        p.setId(pet.getId());
//        p.setName(pet.getName());
//        p.setAge(pet.getAge());
//        p.setBirthday(pet.getBirthday());
//        p.setInformation(pet.getInformation());
//        p.setOwnerId(pet.getOwnerId());
//        return p;
//    }
}
