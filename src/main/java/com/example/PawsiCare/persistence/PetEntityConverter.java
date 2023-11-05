package com.example.PawsiCare.persistence;

import com.example.PawsiCare.domain.Pet;
import com.example.PawsiCare.persistence.entity.PetEntity;

public class PetEntityConverter {

    public Pet fromEntity (PetEntity pet){
        return Pet.builder()
                .id(pet.getId())
                .ownerId(pet.getClient().getId())
                .name(pet.getName())
                .birthday(pet.getBirthday())
                .information(pet.getInformation())
                .build();
    }

    public PetEntity toEntity (Pet pet){
        //TODO: find a solution for the client it's ownerId in domain and dto and client class in entity
        return PetEntity.builder()
                .id(pet.getId())
                //.client(pet.getOwnerId())
                .name(pet.getName())
                .birthday(pet.getBirthday())
                .information(pet.getInformation())
                .build();
    }
}
