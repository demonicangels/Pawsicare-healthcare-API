package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.pet;

import java.util.List;

public interface petManager {
    pet createPet(pet pet);
    pet updatePet(long id, pet pet);
    pet getPet(long id);
    void deletePet(long id);
    List<pet> getPets(long ownerId);
}
