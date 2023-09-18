package com.example.PawsiCare.persistence;
import com.example.PawsiCare.business.Pet;
import com.example.PawsiCare.persistence.DTOs.PetDTO;
import java.util.*;

public interface PetRepository {
    PetDTO createPet(PetDTO pet);
    PetDTO updatePet(long id, PetDTO pet);
    PetDTO getPet(long id);
    void deletePet(long id);
    List<PetDTO> getPets(long ownerId);

}
