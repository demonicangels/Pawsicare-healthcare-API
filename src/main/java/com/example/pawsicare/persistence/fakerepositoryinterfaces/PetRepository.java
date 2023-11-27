package com.example.pawsicare.persistence.fakerepositoryinterfaces;
import com.example.pawsicare.domain.Pet;
import java.util.*;

public interface PetRepository {
    Pet createPet(Pet pet);
    Pet updatePet(long id, Pet pet);
    Pet getPet(long id);
    void deletePet(long id);
    List<Pet> getPets(long ownerId);

}
