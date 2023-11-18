package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.Pet;
import com.example.pawsicare.persistence.entity.ClientEntity;
import com.example.pawsicare.persistence.entity.PetEntity;
import com.example.pawsicare.persistence.jpaRepositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
                .name(pet.getName())
                .client(userRepository.getUserEntityById(pet.getOwnerId()).map(ClientEntity.class :: cast).orElse(null))
                .birthday(pet.getBirthday())
                .information(pet.getInformation())
                .build();
    }
}
