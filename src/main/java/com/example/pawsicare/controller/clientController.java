package com.example.pawsicare.controller;

import com.example.pawsicare.business.DTOs.clientDTO;
import com.example.pawsicare.business.impl.clientConverter;
import com.example.pawsicare.business.requests.createClientRequest;
import com.example.pawsicare.business.requests.updateClientRequest;
import com.example.pawsicare.business.responses.createClientResponse;
import com.example.pawsicare.business.responses.getAllClientsResponse;
import com.example.pawsicare.business.responses.getClientResponse;
import com.example.pawsicare.business.responses.updateClientResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pawsicare.domain.managerinterfaces.clientManager;

import java.util.*;


@RestController
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/clients")
@AllArgsConstructor
public class clientController {

    private final  clientManager clientManager;
    private final clientConverter converter;

    //@RolesAllowed({"Doctor","Client"})
    @GetMapping(params = "id")
    public ResponseEntity<getClientResponse> getClient(@RequestParam(name = "id") long id){
        Optional<clientDTO> client = Optional.ofNullable(converter.toDTO(clientManager.getClient(id)));
        if(client.isPresent()){

            getClientResponse clientResponse = getClientResponse.builder()
                    .client(client.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // @RolesAllowed({"Client"})
    @GetMapping()
    public ResponseEntity<getAllClientsResponse> getClients(){
        Optional<List<clientDTO>> allClients = Optional.ofNullable(clientManager.getClients().stream()
                .map(converter :: toDTO)
                .toList());

        if(allClients.isPresent()){

            getAllClientsResponse clientsResponse = getAllClientsResponse.builder()
                    .clients(allClients.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(clientsResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping()
    public ResponseEntity<createClientResponse> registerClient(@RequestBody @Valid @NotNull createClientRequest request){

        clientDTO client = clientDTO.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Optional<clientDTO> optCreatedClient = Optional.ofNullable(converter.toDTO(clientManager.createClient(converter.fromDTO(client))));
        if(optCreatedClient.isPresent()){

            createClientResponse response = createClientResponse.builder()
                    .client(optCreatedClient.get())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @RolesAllowed({"Client"})
    @PutMapping()
    public ResponseEntity<updateClientResponse> updateClient(@RequestParam(name = "id") long id, @RequestBody @Valid updateClientRequest request){
        clientDTO client = clientDTO.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        Optional<clientDTO> client1 = Optional.ofNullable(converter.toDTO(clientManager.updateClient(converter.fromDTO(client))));

        if(client1.isPresent()){
            updateClientResponse clientResponse = updateClientResponse.builder()
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
