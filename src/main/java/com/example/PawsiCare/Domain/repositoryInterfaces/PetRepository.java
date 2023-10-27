package com.example.PawsiCare.Domain.repositoryInterfaces;
import com.example.PawsiCare.Domain.Pet;
import java.util.*;

public interface PetRepository {
    Pet createPet(Pet pet);
    Pet updatePet(long id, Pet pet);
    Pet getPet(long id);
    void deletePet(long id);
    List<Pet> getPets(long ownerId);

}
