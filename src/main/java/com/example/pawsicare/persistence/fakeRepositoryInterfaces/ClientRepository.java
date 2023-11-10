package com.example.pawsicare.persistence.fakeRepositoryInterfaces;

import com.example.pawsicare.domain.client;

import java.util.List;

public interface ClientRepository {
    client createClient(client client);
    client updateClient(long id, client client);
    client getClient(long id);
    List<client> getClients();
    void deleteClient(long id);
}
