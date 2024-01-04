package com.example.pawsicare.business.converters;

import com.example.pawsicare.business.dto.PetDTO;
import com.example.pawsicare.domain.Pet;
import org.springframework.stereotype.Service;

@Service
public class PetConverter {
    public Pet fromDTO(PetDTO petDTO){

        return Pet.builder()
                .id(petDTO.getId() != 0 ? petDTO.getId() : 0 )
                .ownerId(petDTO.getOwnerId())
                .name(petDTO.getName())
                .birthday(petDTO.getBirthday())
                .age(petDTO.getAge())
                .type(petDTO.getType())
                .gender(petDTO.getGender())
                .information(petDTO.getInformation())
                .build();
    }

    public PetDTO toDTO (Pet pet){

        if(pet == null){
            return new PetDTO();
        }
        return PetDTO.builder()
                .id(pet.getId())
                .ownerId(pet.getOwnerId())
                .name(pet.getName())
                .birthday(pet.getBirthday())
                .age(pet.getAge())
                .type(pet.getType())
                .gender(pet.getGender())
                .information(pet.getInformation())
                .build();
    }
}
