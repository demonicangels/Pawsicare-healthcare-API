package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.pet;
import com.example.pawsicare.persistence.fakeRepositoryInterfaces.PetRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class FakePetRepositoryImpl implements PetRepository {
    private final List<pet> pets;
    private int fakeId = 1;

    public FakePetRepositoryImpl(){
        this.pets = new ArrayList<>();
    }

    @Override
    public pet createPet(pet pet) {
        pet.setId(fakeId);
        fakeId++;

        pets.add(pet);
        return pet;
    }

    @Override
    public pet updatePet(long id, pet pet) {
        Optional<com.example.pawsicare.domain.pet> petOptional = pets.stream().filter(p -> p.getId() == id).findFirst();
        if(petOptional.isPresent()){
            int index = pets.indexOf(petOptional.get());

            com.example.pawsicare.domain.pet peti = petOptional.get();
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
    public pet getPet(long id) {
        Optional<pet> petOptional = pets.stream().filter(p -> p.getId() == id).findFirst();
        if(petOptional.isPresent()){
            return petOptional.get();
        }
        return null;
    }

    @Override
    public void deletePet(long id) {
        pets.removeIf(p -> p.getId() == id);
    }

    @Override
    public List<pet> getPets(long ownerId) {
        return pets.stream().filter(p-> p.getOwnerId() == ownerId).toList();
    }
}
