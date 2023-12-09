package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.Pet;

import java.util.List;

public interface PetManager {
    Pet createPet(Pet pet);
    void updatePet(Pet pet);
    Pet getPet(long id);
    void deletePet(long id);
    List<Pet> getPets(long ownerId);
}
