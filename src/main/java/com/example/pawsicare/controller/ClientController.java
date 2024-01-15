package com.example.pawsicare.controller;

import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.business.exceptions.UserNotAuthenticatedException;
import com.example.pawsicare.business.converters.ClientConverter;
import com.example.pawsicare.business.requests.CreateClientRequest;
import com.example.pawsicare.business.requests.UpdateClientRequest;
import com.example.pawsicare.business.responses.CreateClientResponse;
import com.example.pawsicare.business.responses.GetAllClientsResponse;
import com.example.pawsicare.business.responses.GetClientResponse;
import com.example.pawsicare.business.responses.UpdateClientResponse;
import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pawsicare.domain.managerinterfaces.ClientManager;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientManager clientManager;
    private final ClientConverter converter;
    private final AccessToken accessToken;

    private String errorMsg = "User not allowed!";



    @RolesAllowed({"Client", "Doctor"})
    @GetMapping("/clientInfo")
    public ResponseEntity<GetClientResponse> getClient(@RequestParam(name = "id") Long id) throws UserNotAuthenticatedException {

        Long userId = accessToken.getId();
        boolean isClient = accessToken.hasRole(Role.Client.name());
        boolean isDoctor = accessToken.hasRole(Role.Doctor.name());

        if(userId.equals(id) && (isClient || isDoctor)){
            Optional<ClientDTO> client = Optional.ofNullable(converter.toDTO(clientManager.getClient(id)));
            if(client.isPresent()){

                GetClientResponse clientResponse = GetClientResponse.builder()
                        .client(client.get())
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
            }
        }
        throw new UserNotAuthenticatedException(errorMsg);
    }

    @RolesAllowed({"Doctor", "Client"})
    @GetMapping()
    public ResponseEntity<GetAllClientsResponse> getClients(){

        //possibly neither doctors need this nor clients possible method for admins
        Optional<List<ClientDTO>> allClients = Optional.ofNullable(clientManager.getClients().stream()
                .map(converter :: toDTO)
                .toList());

        if(allClients.isPresent()){

            GetAllClientsResponse clientsResponse = GetAllClientsResponse.builder()
                    .clients(allClients.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(clientsResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping()
    public ResponseEntity<CreateClientResponse> registerClient(@RequestBody @Valid @NotNull CreateClientRequest request){

        ClientDTO client = ClientDTO.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Optional<ClientDTO> optCreatedClient = Optional.ofNullable(converter.toDTO(clientManager.createClient(converter.fromDTO(client))));
        if(optCreatedClient.isPresent()){

            CreateClientResponse response = CreateClientResponse.builder()
                    .client(optCreatedClient.get())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @RolesAllowed({"Client"})
    @PutMapping()
    public ResponseEntity<UpdateClientResponse> updateClient(@RequestBody @Valid UpdateClientRequest request) throws UserNotAuthenticatedException {


        Long userId = accessToken.getId();
        boolean isClient = accessToken.hasRole(Role.Client.name());

        if (userId.equals(request.getId()) && isClient) {

            ClientDTO client = ClientDTO.builder()
                    .id(userId)
                    .phoneNumber(request.getPhoneNumber())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .build();

            Client updatedClient = clientManager.updateClient(converter.fromDTO(client));

            UpdateClientResponse clientResponse = UpdateClientResponse.builder()
                    .updatedClient(converter.toDTO(updatedClient))
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientResponse);
        }
        throw new UserNotAuthenticatedException(errorMsg);
    }
}
