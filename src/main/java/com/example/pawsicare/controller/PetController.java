package com.example.pawsicare.controller;

import com.example.pawsicare.business.DTOs.PetDTO;
import com.example.pawsicare.business.impl.PetConverter;
import com.example.pawsicare.business.requests.CreatePetRequest;
import com.example.pawsicare.business.requests.UpdatePetRequest;
import com.example.pawsicare.business.responses.CreatePetResponse;
import com.example.pawsicare.business.responses.GetAllPetsResponse;
import com.example.pawsicare.business.responses.GetPetResponse;
import com.example.pawsicare.business.responses.UpdatePetResponse;
import com.example.pawsicare.domain.Gender;
import com.example.pawsicare.domain.managerinterfaces.PetManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {

    private final PetManager petManager;

    private final PetConverter converter;


    //@RolesAllowed({"Client"})
    @PostMapping()
    public ResponseEntity<CreatePetResponse> createPet(@RequestBody @Valid CreatePetRequest request){
        PetDTO pet = PetDTO.builder()
                .ownerId(request.getOwnerId())
                .name(request.getName())
                .information(request.getInformation())
                .type(request.getType())
                .gender(request.getGender())
                .birthday(request.getBirthday())
                .build();

        Optional<PetDTO> peti = Optional.ofNullable(converter.toDTO(petManager.createPet(converter.fromDTO(pet))));

        if(peti.isPresent()){

            CreatePetResponse petResponse = CreatePetResponse.builder()
                    .pet(pet)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(petResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(params = "ownerId")
    public ResponseEntity<GetAllPetsResponse> getPetsByOwnerId(@RequestParam(name = "ownerId", required = false) long ownerId){
        Optional<List<PetDTO>> pets = Optional.ofNullable(petManager.getPets(ownerId).stream()
                .map(converter :: toDTO)
                .toList());
        if(pets.isPresent()){

            GetAllPetsResponse allPets = GetAllPetsResponse.builder()
                    .pets(pets.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(allPets);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(params = "id")
    public ResponseEntity<GetPetResponse> getPetById(@RequestParam(name = "id") long petId){
        Optional<PetDTO> pet = Optional.ofNullable(converter.toDTO(petManager.getPet(petId)));
        if(pet.isPresent()){

            GetPetResponse petResponse = GetPetResponse.builder()
                    .pet(pet.get())
                    .build();

            return ResponseEntity.ok(petResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping(params = "id")
    public ResponseEntity<UpdatePetResponse> updatePet(@RequestParam(name = "id") long id, @RequestBody @Valid UpdatePetRequest request){
        PetDTO pet = PetDTO.builder()
                .ownerId(request.getOwnerId())
                .name(request.getName())
                .information(request.getInformation())
                .build();

        Optional<PetDTO> peti = Optional.ofNullable(converter.toDTO(petManager.updatePet(converter.fromDTO(pet))));

        if(peti.isPresent()){

            UpdatePetResponse petResponse = UpdatePetResponse.builder()
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
