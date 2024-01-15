package com.example.pawsicare.controller;

import com.example.pawsicare.business.dto.PetDTO;
import com.example.pawsicare.business.exceptions.UserNotAuthenticatedException;
import com.example.pawsicare.business.converters.PetConverter;
import com.example.pawsicare.business.requests.CreatePetRequest;
import com.example.pawsicare.business.requests.UpdatePetRequest;
import com.example.pawsicare.business.responses.CreatePetResponse;
import com.example.pawsicare.business.responses.GetAllPetsResponse;
import com.example.pawsicare.business.responses.GetPetResponse;
import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.domain.Role;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.PetManager;
import com.example.pawsicare.persistence.converters.UserEntityConverter;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetManager petManager;

    private final PetConverter converter;

    private final UserRepository userRepository;
    private final UserEntityConverter userEntityConverter;
    private final AccessToken accessToken;

    private String errorMsg = "User not allowed!";

    @RolesAllowed({"Client"})
    @PostMapping()
    public ResponseEntity<CreatePetResponse> createPet(@RequestBody @Valid CreatePetRequest request) throws UserNotAuthenticatedException {


        Optional<User> userFound = userRepository.getUserEntityById(accessToken.getId())
                .map(userEntityConverter::fromUserEntity)
                .map(Optional::of)
                .orElse(Optional.empty());

        if(!userFound.isEmpty()){
            Long userId = accessToken.getId();
            boolean isClient = accessToken.hasRole(Role.Client.name());

            if(userId.equals(request.getOwnerId()) && isClient){

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
            }

        }
        throw new UserNotAuthenticatedException(errorMsg);
    }

    @RolesAllowed({"Client","Doctor"})
    @GetMapping()
    public ResponseEntity<GetAllPetsResponse> getPetsByOwnerId(@RequestParam(name = "ownerId") Long ownerId) throws UserNotAuthenticatedException {

        Optional<User> userFound = userRepository.getUserEntityById(accessToken.getId())
                .map(userEntityConverter::fromUserEntity)
                .map(Optional::of)
                .orElse(Optional.empty());

        if(!userFound.isEmpty()){
            Long userId = accessToken.getId();
            boolean isClient = accessToken.hasRole(Role.Client.name());
            boolean isDoctor = accessToken.hasRole(Role.Doctor.name());

            if(userId.equals(ownerId) && isClient || isDoctor){

                Optional<List<PetDTO>> pets = Optional.ofNullable(petManager.getPets(ownerId)
                                .stream()
                                .map(converter::toDTO)
                                .toList());

                boolean isEmpty = !pets.isEmpty();

                GetAllPetsResponse allPets = GetAllPetsResponse.builder()
                        .pets(isEmpty ? pets.get() : new ArrayList<>())
                        .build();

                    return ResponseEntity.status(HttpStatus.OK).body(allPets);
            }
        }
        throw new UserNotAuthenticatedException(errorMsg);
    }

    @RolesAllowed({"Client"})
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

    @RolesAllowed({"Client"})
    @PutMapping()
    public ResponseEntity<Void> updatePet(@RequestBody @Valid UpdatePetRequest request) throws UserNotAuthenticatedException {

        Optional<User> userFound = userRepository.getUserEntityById(accessToken.getId())
                .map(userEntityConverter::fromUserEntity)
                .map(Optional::of)
                .orElse(Optional.empty());

        if(!userFound.isEmpty()) {
            Long userId = accessToken.getId();
            boolean isClient = accessToken.hasRole(Role.Client.name());

            if (userId.equals(request.getOwnerId()) && isClient) {
                PetDTO pet = PetDTO.builder()
                        .id(request.getId())
                        .ownerId(request.getOwnerId())
                        .name(request.getName())
                        .information(request.getInformation())
                        .build();

                petManager.updatePet(converter.fromDTO(pet));


                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }
        }
        throw new UserNotAuthenticatedException(errorMsg);
    }

    @RolesAllowed({"Client"})
    @DeleteMapping()
    public ResponseEntity<Void> deletePet(@RequestParam(name = "id") long id) throws UserNotAuthenticatedException {



        Optional<User> userFound = userRepository.getUserEntityById(accessToken.getId())
                .map(userEntityConverter::fromUserEntity)
                .map(Optional::of)
                .orElse(Optional.empty());

        if (!userFound.isEmpty()) {
            boolean isClient = accessToken.hasRole(Role.Client.name());

            if (isClient) {
                petManager.deletePet(id);
                return ResponseEntity.ok().build();
            }
        }
        throw new UserNotAuthenticatedException(errorMsg);
    }
}
