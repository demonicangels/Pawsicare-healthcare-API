package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.Client;

import java.util.List;

public interface ClientManager {
    Client createClient(Client client);
    Client updateClient(Client client);

    Client getClient(long id);

    List<Client> getClients();
    void deleteClient(long id);

}
