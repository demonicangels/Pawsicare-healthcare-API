package com.example.pawsicare.persistence.fakeRepositoryInterfaces;
import com.example.pawsicare.domain.pet;
import java.util.*;

public interface PetRepository {
    pet createPet(pet pet);
    pet updatePet(long id, pet pet);
    pet getPet(long id);
    void deletePet(long id);
    List<pet> getPets(long ownerId);

}
