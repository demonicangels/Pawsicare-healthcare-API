package com.example.PawsiCare.persistence;

import com.example.PawsiCare.domain.Pet;
import com.example.PawsiCare.domain.repositoryInterfaces.PetRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;


@Repository
public class FakePetRepositoryImpl implements PetRepository {
    private final List<Pet> pets;
    private int fakeId = 1;

    public FakePetRepositoryImpl(){
        this.pets = new ArrayList<>();
    }

    @Override
    public Pet createPet(Pet pet) {
        pet.setId(fakeId);
        fakeId++;

        pets.add(pet);
        return pet;
    }

    @Override
    public Pet updatePet(long id, Pet pet) {
        Optional<Pet> petOptional = pets.stream().filter(p -> p.getId() == id).findFirst();
        if(petOptional.isPresent()){
            int index = pets.indexOf(petOptional.get());

            Pet peti = petOptional.get();
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
    public Pet getPet(long id) {
        Optional<Pet> petOptional = pets.stream().filter(p -> p.getId() == id).findFirst();
        if(petOptional.isPresent()){
            Pet pet = petOptional.get();
            return pet;
        }
        return null;
    }

    @Override
    public void deletePet(long id) {
        pets.removeIf(p -> p.getId() == id);
    }

    @Override
    public List<Pet> getPets(long ownerId) {
        List<Pet> ownersPets = pets.stream().filter(p-> p.getOwnerId() == ownerId).collect(Collectors.toList());
        return ownersPets;
    }
}
