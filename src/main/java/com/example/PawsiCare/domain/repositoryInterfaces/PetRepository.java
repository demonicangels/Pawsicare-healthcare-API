package com.example.PawsiCare.domain.repositoryInterfaces;
import com.example.PawsiCare.domain.Pet;
import java.util.*;

public interface PetRepository {
    Pet createPet(Pet pet);
    Pet updatePet(long id, Pet pet);
    Pet getPet(long id);
    void deletePet(long id);
    List<Pet> getPets(long ownerId);

}