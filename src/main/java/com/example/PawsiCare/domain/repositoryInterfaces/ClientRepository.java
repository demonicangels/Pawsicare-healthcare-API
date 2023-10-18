package com.example.PawsiCare.domain.repositoryInterfaces;

import com.example.PawsiCare.domain.Client;

import java.util.List;

public interface ClientRepository {
    Client createClient(Client client);
    Client updateClient(long id, Client client);
    Client getClient(long id);
    List<Client> getClients();
    void deleteClient(long id);
}
