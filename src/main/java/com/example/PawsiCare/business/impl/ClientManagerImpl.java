package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.domain.Client;
import com.example.PawsiCare.domain.managerInterfaces.ClientManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.PawsiCare.domain.fakeRepositoryInterfaces.ClientRepository;

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
        clientRepository.getClients().forEach(
            returnedClients::add
        );
        return returnedClients;
    }

    @Override
    public void deleteClient(long id) {
        clientRepository.deleteClient(id);
    }

}
