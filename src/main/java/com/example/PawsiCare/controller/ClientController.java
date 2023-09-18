package com.example.PawsiCare.controller;

import com.example.PawsiCare.business.Client;
import com.example.PawsiCare.business.ClientManager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientManager clientManager;


    @GetMapping(params = "id")
    public ResponseEntity<Client> getClient(@PathVariable long id){
        Optional<Client> client = Optional.ofNullable(clientManager.getClient(id));
        if(client.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(client.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping()
    public ResponseEntity<List<Client>> getClients(){
        Optional<List<Client>> allClients = Optional.ofNullable(clientManager.getClients());
        if(allClients.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(allClients.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping()
    public ResponseEntity<Client> registerClient(@RequestBody Client client){
        Optional<Client> createdC = Optional.ofNullable(clientManager.createClient(client));
        if(createdC.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdC.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping()
    public ResponseEntity<Client> updateClient(@RequestParam(name = "id") long id, @RequestBody Client client){
        Optional<Client> client1 = Optional.ofNullable(clientManager.updateClient(id, client));
        if(client1.isPresent()){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(client1.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteClient(@RequestParam(name = "id") long id){
        clientManager.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
