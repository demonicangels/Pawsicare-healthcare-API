package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.pet;
import com.example.pawsicare.persistence.entity.clientEntity;
import com.example.pawsicare.persistence.entity.petEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class petEntityConverter {

    private final com.example.pawsicare.persistence.jpaRepositories.userRepository userRepository;

    public pet fromEntity (petEntity pet){
        return com.example.pawsicare.domain.pet.builder()
                .id(pet.getId())
                .name(pet.getName())
                .ownerId(pet.getClient().getId())
                .birthday(pet.getBirthday())
                .information(pet.getInformation())
                .build();
    }

    public petEntity toEntity (pet pet){
        return petEntity.builder()
                .name(pet.getName())
                .client(userRepository.getUserEntityById(pet.getOwnerId()).map(clientEntity.class :: cast).orElse(null))
                .birthday(pet.getBirthday())
                .information(pet.getInformation())
                .build();
    }
}
