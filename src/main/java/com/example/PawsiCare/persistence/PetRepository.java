package com.example.PawsiCare.persistence;
import com.example.PawsiCare.business.domain.Pet;
import java.util.*;

public interface PetRepository {
    Pet createPet(Pet pet);
    Pet updatePet(long id, Pet pet);
    Pet getPet(long id);
    void deletePet(long id);
    List<Pet> getPets(long ownerId);

}
