package com.example.PawsiCare.persistence.impl;

import com.example.PawsiCare.persistence.DTOs.PetDTO;
import com.example.PawsiCare.persistence.PetRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;


@Repository
public class FakePetRepositoryImpl implements PetRepository {
    private final List<PetDTO> pets;
    private int fakeId = 1;

    public FakePetRepositoryImpl(){
        this.pets = new ArrayList<>();
    }

    @Override
    public PetDTO createPet(PetDTO pet) {
        pet.setId(fakeId);
        fakeId++;

        pets.add(pet);
        return pet;
    }

    @Override
    public PetDTO updatePet(long id, PetDTO pet) {
        Optional<PetDTO> petOptional = pets.stream().filter(p -> p.getId() == id).findFirst();
        if(petOptional.isPresent()){
            int index = pets.indexOf(petOptional.get());

            PetDTO peti = petOptional.get();
            peti.setAge(pet.getAge());
            peti.setName(pet.getName());
            peti.setBirthday(pet.getBirthday());
            peti.setInformation(pet.getInformation());

            pets.set(index,peti);

            return peti;
        }
        return null;
    }

    @Override
    public PetDTO getPet(long id) {
        Optional<PetDTO> petOptional = pets.stream().filter(p -> p.getId() == id).findFirst();
        if(petOptional.isPresent()){
            PetDTO pet = petOptional.get();
            return pet;
        }
        return null;
    }

    @Override
    public void deletePet(long id) {
        pets.removeIf(p -> p.getId() == id);
    }

    @Override
    public List<PetDTO> getPets(long ownerId) {
        List<PetDTO> ownersPets = pets.stream().filter(p-> p.getOwnerId() == ownerId).collect(Collectors.toList());
        return ownersPets;
    }
}
