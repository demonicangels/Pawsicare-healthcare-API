package com.example.PawsiCare.persistence;

import com.example.PawsiCare.domain.Pet;
import com.example.PawsiCare.persistence.entity.ClientEntity;
import com.example.PawsiCare.persistence.entity.PetEntity;
import com.example.PawsiCare.persistence.jpaRepositories.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PetEntityConverter {

    private final UserRepository userRepository;

    public Pet fromEntity (PetEntity pet){
        return Pet.builder()
                .id(pet.getId())
                .name(pet.getName())
                .ownerId(pet.getClient().getId())
                .birthday(pet.getBirthday())
                .information(pet.getInformation())
                .build();
    }

    public PetEntity toEntity (Pet pet){
        return PetEntity.builder()
                .id(pet.getId())
                .name(pet.getName())
                .client((ClientEntity)userRepository.getUserEntityById(pet.getOwnerId()).get())
                .birthday(pet.getBirthday())
                .information(pet.getInformation())
                .build();
    }
}
