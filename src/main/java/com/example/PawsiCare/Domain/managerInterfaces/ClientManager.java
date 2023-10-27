package com.example.PawsiCare.Domain.managerInterfaces;

import com.example.PawsiCare.Domain.Client;

import java.util.List;

public interface ClientManager {
    Client createClient(Client client);
    Client updateClient(long id, Client client);

    Client getClient(long id);

    List<Client> getClients();
    void deleteClient(long id);

}
