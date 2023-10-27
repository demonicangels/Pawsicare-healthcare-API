package com.example.PawsiCare.Domain.managerInterfaces;

import com.example.PawsiCare.Domain.Pet;

import java.util.List;

public interface PetManager {
    Pet createPet(Pet pet);
    Pet updatePet(long id, Pet pet);
    Pet getPet(long id);
    void deletePet(long id);
    List<Pet> getPets(long ownerId);
}
