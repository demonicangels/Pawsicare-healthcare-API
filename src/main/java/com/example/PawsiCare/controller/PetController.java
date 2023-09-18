package com.example.PawsiCare.controller;

import com.example.PawsiCare.business.Pet;
import com.example.PawsiCare.business.PetManager;
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
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet){
        Optional<Pet> peti = Optional.ofNullable(petManager.createPet(pet));
        if(peti.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(peti.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(params = "ownerId")
    public ResponseEntity<List<Pet>> getPetsByOwnerId(@RequestParam(name = "ownerId", required = false) long ownerId){
        Optional<List<Pet>> pets = Optional.ofNullable(petManager.getPets(ownerId));
        if(pets.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(pets.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(params = "id")
    public ResponseEntity<Pet> getPetById(@RequestParam(name = "id") long petId){
        Optional<Pet> pet = Optional.ofNullable(petManager.getPet(petId));
        if(pet.isPresent()){
            return ResponseEntity.ok(pet.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping(params = "id")
    public ResponseEntity<Pet> updatePet(@RequestParam(name = "id") long id, @RequestBody Pet pet){
        Optional<Pet> peti = Optional.ofNullable(petManager.updatePet(id,pet));
        if(peti.isPresent()){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(peti.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deletePet(@RequestParam(name = "id") long id){
        petManager.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    //createpetrequest (requests and response for every mapping)
}
