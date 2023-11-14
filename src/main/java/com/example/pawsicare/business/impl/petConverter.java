package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.DTOs.PetDTO;
import com.example.pawsicare.domain.Pet;
import org.springframework.stereotype.Service;

@Service
public class petConverter {
    public Pet fromDTO(PetDTO petDTO){

        return Pet.builder()
                .id(petDTO.getId())
                .ownerId(petDTO.getOwnerId())
                .name(petDTO.getName())
                .birthday(petDTO.getBirthday())
                .age(petDTO.getAge())
                .information(petDTO.getInformation())
                .build();
    }

    public PetDTO toDTO (Pet pet){

        return PetDTO.builder()
                .id(pet.getId())
                .ownerId(pet.getOwnerId())
                .name(pet.getName())
                .birthday(pet.getBirthday())
                .age(pet.getAge())
                .information(pet.getInformation())
                .build();
    }
}
