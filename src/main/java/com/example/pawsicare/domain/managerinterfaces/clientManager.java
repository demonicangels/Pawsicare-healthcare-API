package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.client;

import java.util.List;

public interface clientManager {
    client createClient(client client);
    client updateClient(client client);

    client getClient(long id);

    List<client> getClients();
    void deleteClient(long id);

}
