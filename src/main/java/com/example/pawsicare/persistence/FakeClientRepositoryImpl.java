package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.client;
import org.springframework.stereotype.Repository;
import com.example.pawsicare.persistence.fakeRepositoryInterfaces.ClientRepository;

import java.util.*;

@Repository
public class FakeClientRepositoryImpl implements ClientRepository {
    private final List<client> clients;
    Long fakeID = 1L;

    public FakeClientRepositoryImpl(){
        this.clients = new ArrayList<>();
    }
    @Override
    public client createClient(client client) {
        client.setId(fakeID);
        fakeID++;

        clients.add(client);
        return client;
    }

    @Override
    public client updateClient(long id, client client) {
        Optional<com.example.pawsicare.domain.client> cli = clients.stream().filter(c -> c.getId() == id).findFirst();
        if(cli.isPresent()){
            int index = clients.indexOf(cli.get());

            com.example.pawsicare.domain.client c = cli.get();
            c.setName(client.getName());
            c.setEmail(client.getEmail());
            c.setPhoneNumber(client.getPhoneNumber());
            c.setPassword(client.getPassword());

            clients.set(index, c);

            return c;
        }
        return null;
    }

    @Override
    public client getClient(long id) {
        Optional<client> cli = clients.stream().filter(c -> c.getId() == id).findFirst();
        if(cli.isPresent()){

            return cli.get();
        }
        return null;
    }

    @Override
    public List<client> getClients() {
        return clients;
    }

    @Override
    public void deleteClient(long id) {
        clients.removeIf(c -> c.getId() == id);
    }
}
