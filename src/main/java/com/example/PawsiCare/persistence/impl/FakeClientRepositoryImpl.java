package com.example.PawsiCare.persistence.impl;

import com.example.PawsiCare.persistence.DTOs.ClientDTO;
import org.springframework.stereotype.Repository;
import com.example.PawsiCare.persistence.ClientRepository;

import java.util.*;

@Repository
public class FakeClientRepositoryImpl implements ClientRepository {
    private final List<ClientDTO> clients;
    int fakeID = 1;

    public FakeClientRepositoryImpl(){
        this.clients = new ArrayList<>();
    }
    @Override
    public ClientDTO createClient(ClientDTO client) {
        client.setId(fakeID);
        fakeID++;

        clients.add(client);
        return client;
    }

    @Override
    public ClientDTO updateClient(long id, ClientDTO client) {
        Optional<ClientDTO> cli = clients.stream().filter(c -> c.getId() == id).findFirst();
        if(cli.isPresent()){
            int index = clients.indexOf(cli.get());

            ClientDTO clientDTO = cli.get();
            clientDTO.setName(client.getName());
            clientDTO.setEmail(client.getEmail());
            clientDTO.setPhoneNumber(client.getPhoneNumber());
            clientDTO.setPassword(client.getPassword());

            clients.set(index, clientDTO);

            return clientDTO;
        }
        return null;
    }

    @Override
    public ClientDTO getClient(long id) {
        Optional<ClientDTO> cli = clients.stream().filter(c -> c.getId() == id).findFirst();
        if(cli.isPresent()){
            ClientDTO client = cli.get();
            return client;
        }
        return null;
    }

    @Override
    public List<ClientDTO> getClients() {
        return clients;
    }

    @Override
    public void deleteClient(long id) {
        clients.removeIf(c -> c.getId() == id);
    }
}
