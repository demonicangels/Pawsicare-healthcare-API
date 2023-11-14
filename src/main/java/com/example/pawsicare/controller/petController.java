package com.example.pawsicare.controller;

import com.example.pawsicare.business.DTOs.PetDTO;
import com.example.pawsicare.business.impl.petConverter;
import com.example.pawsicare.business.requests.createPetRequest;
import com.example.pawsicare.business.requests.updatePetRequest;
import com.example.pawsicare.business.responses.createPetResponse;
import com.example.pawsicare.business.responses.getAllPetsResponse;
import com.example.pawsicare.business.responses.getPetResponse;
import com.example.pawsicare.business.responses.updatePetResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class petController {

    private final com.example.pawsicare.domain.managerinterfaces.petManager petManager;

    private final petConverter converter;


    @PostMapping()
    public ResponseEntity<createPetResponse> createPet(@RequestBody @Valid createPetRequest request){
        PetDTO pet = PetDTO.builder()
                .ownerId(request.getOwnerId())
                .name(request.getName())
                .information(request.getInformation())
                .age(request.getAge())
                .birthday(request.getBirthday())
                .build();

        Optional<PetDTO> peti = Optional.ofNullable(converter.toDTO(petManager.createPet(converter.fromDTO(pet))));

        if(peti.isPresent()){

            createPetResponse petResponse = createPetResponse.builder()
                    .pet(pet)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(petResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(params = "ownerId")
    public ResponseEntity<getAllPetsResponse> getPetsByOwnerId(@RequestParam(name = "ownerId", required = false) long ownerId){
        Optional<List<PetDTO>> pets = Optional.ofNullable(petManager.getPets(ownerId).stream()
                .map(converter :: toDTO)
                .toList());
        if(pets.isPresent()){

            getAllPetsResponse allPets = getAllPetsResponse.builder()
                    .pets(pets.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(allPets);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(params = "id")
    public ResponseEntity<getPetResponse> getPetById(@RequestParam(name = "id") long petId){
        Optional<PetDTO> pet = Optional.ofNullable(converter.toDTO(petManager.getPet(petId)));
        if(pet.isPresent()){

            getPetResponse petResponse = getPetResponse.builder()
                    .pet(pet.get())
                    .build();

            return ResponseEntity.ok(petResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping(params = "id")
    public ResponseEntity<updatePetResponse> updatePet(@RequestParam(name = "id") long id, @RequestBody @Valid updatePetRequest request){
        PetDTO pet = PetDTO.builder()
                .ownerId(request.getOwnerId())
                .name(request.getName())
                .information(request.getInformation())
                .build();

        Optional<PetDTO> peti = Optional.ofNullable(converter.toDTO(petManager.updatePet(id,converter.fromDTO(pet))));

        if(peti.isPresent()){

            updatePetResponse petResponse = updatePetResponse.builder()
                    .updatedPet(pet)
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(petResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deletePet(@RequestParam(name = "id") long id){
        petManager.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}
