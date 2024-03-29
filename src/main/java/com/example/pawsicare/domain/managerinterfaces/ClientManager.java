package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.RefreshToken;

import java.util.List;

public interface ClientManager {
    Client createClient(Client client);
    Client updateClient(Client client);

    Client getClient(long id);

    List<Client> getClients();
    void deleteUser(long id, RefreshToken token);

}
