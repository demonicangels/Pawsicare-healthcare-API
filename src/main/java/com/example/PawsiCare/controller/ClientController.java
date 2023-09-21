package com.example.PawsiCare.controller;

import com.example.PawsiCare.business.domain.Client;
import com.example.PawsiCare.business.ClientManager;
import com.example.PawsiCare.business.requests.CreateClientRequest;
import com.example.PawsiCare.business.requests.UpdateClientRequest;
import com.example.PawsiCare.business.responses.CreateClientResponse;
import com.example.PawsiCare.business.responses.GetAllClientsResponse;
import com.example.PawsiCare.business.responses.GetClientResponse;
import com.example.PawsiCare.business.responses.UpdateClientResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientManager clientManager;


    @GetMapping(params = "id")
    public ResponseEntity<GetClientResponse> getClient(@RequestParam(name = "id") long id){
        Optional<Client> client = Optional.ofNullable(clientManager.getClient(id));
        if(client.isPresent()){

            GetClientResponse clientResponse = GetClientResponse.builder()
                    .client(client.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping()
    public ResponseEntity<GetAllClientsResponse> getClients(){
        Optional<List<Client>> allClients = Optional.ofNullable(clientManager.getClients());
        if(allClients.isPresent()){

            GetAllClientsResponse clientsResponse = GetAllClientsResponse.builder()
                    .clients(allClients.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(clientsResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping()
    public ResponseEntity<CreateClientResponse> registerClient(@RequestBody @Valid CreateClientRequest request){

        Client client = Client.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Optional<Client> optCreatedClient = Optional.ofNullable(clientManager.createClient(client));
        if(optCreatedClient.isPresent()){

            CreateClientResponse response = CreateClientResponse.builder()
                    .client(optCreatedClient.get())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping()
    public ResponseEntity<UpdateClientResponse> updateClient(@RequestParam(name = "id") long id, @RequestBody @Valid UpdateClientRequest request){
        Client client = Client.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        Optional<Client> client1 = Optional.ofNullable(clientManager.updateClient(id,client));

        if(client1.isPresent()){
            UpdateClientResponse clientResponse = UpdateClientResponse.builder()
                    .updatedClient(client1.get())
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteClient(@RequestParam(name = "id") long id){
        clientManager.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
