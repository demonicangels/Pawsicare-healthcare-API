package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.DTOs.petDTO;
import com.example.pawsicare.domain.pet;
import org.springframework.stereotype.Service;

@Service
public class petConverter {
    public pet fromDTO(petDTO petDTO){

        return pet.builder()
                .id(petDTO.getId())
                .ownerId(petDTO.getOwnerId())
                .name(petDTO.getName())
                .birthday(petDTO.getBirthday())
                .age(petDTO.getAge())
                .information(petDTO.getInformation())
                .build();
    }

    public petDTO toDTO (pet pet){

        return petDTO.builder()
                .id(pet.getId())
                .ownerId(pet.getOwnerId())
                .name(pet.getName())
                .birthday(pet.getBirthday())
                .age(pet.getAge())
                .information(pet.getInformation())
                .build();
    }
}
