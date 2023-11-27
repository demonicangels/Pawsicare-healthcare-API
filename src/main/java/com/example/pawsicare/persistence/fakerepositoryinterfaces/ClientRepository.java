package com.example.pawsicare.persistence.fakerepositoryinterfaces;

import com.example.pawsicare.domain.Client;

import java.util.List;

public interface ClientRepository {
    Client createClient(Client client);
    Client updateClient(long id, Client client);
    Client getClient(long id);
    List<Client> getClients();
    void deleteClient(long id);
}
