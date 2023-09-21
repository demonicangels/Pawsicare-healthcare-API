package com.example.PawsiCare.controller;

import com.example.PawsiCare.business.domain.Pet;
import com.example.PawsiCare.business.PetManager;
import com.example.PawsiCare.business.requests.CreatePetRequest;
import com.example.PawsiCare.business.requests.UpdatePetRequest;
import com.example.PawsiCare.business.responses.CreatePetResponse;
import com.example.PawsiCare.business.responses.GetAllPetsResponse;
import com.example.PawsiCare.business.responses.GetPetResponse;
import com.example.PawsiCare.business.responses.UpdatePetResponse;
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


    @PostMapping()
    public ResponseEntity<CreatePetResponse> createPet(@RequestBody @Valid CreatePetRequest request){
        Pet pet = Pet.builder()
                .ownerId(request.getOwnerId())
                .name(request.getName())
                .information(request.getInformation())
                .age(request.getAge())
                .birthday(request.getBirthday())
                .build();

        Optional<Pet> peti = Optional.ofNullable(petManager.createPet(pet));

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
        Optional<List<Pet>> pets = Optional.ofNullable(petManager.getPets(ownerId));
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
        Optional<Pet> pet = Optional.ofNullable(petManager.getPet(petId));
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
        Pet pet = Pet.builder()
                .ownerId(request.getOwnerId())
                .name(request.getName())
                .information(request.getInformation())
                .build();

        Optional<Pet> peti = Optional.ofNullable(petManager.updatePet(id,pet));

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
