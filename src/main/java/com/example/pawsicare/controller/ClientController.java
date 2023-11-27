package com.example.pawsicare.controller;

import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.business.impl.ClientConverter;
import com.example.pawsicare.business.requests.CreateClientRequest;
import com.example.pawsicare.business.requests.UpdateClientRequest;
import com.example.pawsicare.business.responses.CreateClientResponse;
import com.example.pawsicare.business.responses.GetAllClientsResponse;
import com.example.pawsicare.business.responses.GetClientResponse;
import com.example.pawsicare.business.responses.UpdateClientResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pawsicare.domain.managerinterfaces.ClientManager;

import java.util.*;


@RestController
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientManager clientManager;
    private final ClientConverter converter;

    //@RolesAllowed({"Doctor","Client"})
    @GetMapping(params = "id")
    public ResponseEntity<GetClientResponse> getClient(@RequestParam(name = "id") long id){
        Optional<ClientDTO> client = Optional.ofNullable(converter.toDTO(clientManager.getClient(id)));
        if(client.isPresent()){

            GetClientResponse clientResponse = GetClientResponse.builder()
                    .client(client.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // @RolesAllowed({"Client"})
    @GetMapping()
    public ResponseEntity<GetAllClientsResponse> getClients(){
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
    public ResponseEntity<UpdateClientResponse> updateClient(@RequestParam(name = "id") long id, @RequestBody @Valid UpdateClientRequest request){
        ClientDTO client = ClientDTO.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        Optional<ClientDTO> client1 = Optional.ofNullable(converter.toDTO(clientManager.updateClient(converter.fromDTO(client))));

        if(client1.isPresent()){
            UpdateClientResponse clientResponse = UpdateClientResponse.builder()
                    .updatedClient(client1.get())
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @RolesAllowed({"Client"})
    @DeleteMapping()
    public ResponseEntity<Void> deleteClient(@RequestParam(name = "id") long id){
        clientManager.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
