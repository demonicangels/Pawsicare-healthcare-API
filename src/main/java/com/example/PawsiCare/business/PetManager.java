package com.example.PawsiCare.business;

import com.example.PawsiCare.business.domain.Pet;

import java.util.List;

public interface PetManager {
    Pet createPet(Pet pet);
    Pet updatePet(long id, Pet pet);
    Pet getPet(long id);
    void deletePet(long id);
    List<Pet> getPets(long ownerId);
}
