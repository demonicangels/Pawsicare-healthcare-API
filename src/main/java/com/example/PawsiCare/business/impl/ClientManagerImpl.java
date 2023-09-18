package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.business.domain.Client;
import com.example.PawsiCare.business.ClientManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.PawsiCare.persistence.ClientRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class ClientManagerImpl implements ClientManager {
    private final ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {
        return clientRepository.createClient(client);
    }

    @Override
    public Client updateClient(long id, Client client) {

        return clientRepository.updateClient(id,client);
    }

    @Override
    public Client getClient(long id) {
        return clientRepository.getClient(id);
    }

    @Override
    public List<Client> getClients() {
        List<Client> returnedClients = new ArrayList<>();
        clientRepository.getClients().forEach(c -> {
            returnedClients.add(c);
        });
        return returnedClients;
    }

    @Override
    public void deleteClient(long id) {
        clientRepository.deleteClient(id);
    }


//    private ClientDTO ToDTO(Client client){
//        ClientDTO c = new ClientDTO();
//        c.setId(client.getId());
//        c.setEmail(client.getEmail());
//        c.setName(client.getName());
//        c.setPassword(client.getPassword());
//        c.setPhoneNumber(client.getPhoneNumber());
//
//        return c;
//    }

//    private Client FromDTO(ClientDTO client){
//        Client c = new Client();
//        c.setId(client.getId());
//        c.setEmail(client.getEmail());
//        c.setName(client.getName());
//        c.setPassword(client.getPassword());
//        c.setPhoneNumber(client.getPhoneNumber());
//
//        return c;
//    }

}
