package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.Client;
import org.springframework.stereotype.Repository;
import com.example.pawsicare.persistence.fakeRepositoryInterfaces.ClientRepository;

import java.util.*;

@Repository
public class FakeClientRepositoryImpl implements ClientRepository {
    private final List<Client> Clients;
    Long fakeID = 1L;

    public FakeClientRepositoryImpl(){
        this.Clients = new ArrayList<>();
    }
    @Override
    public Client createClient(Client client) {
        client.setId(fakeID);
        fakeID++;

        Clients.add(client);
        return client;
    }

    @Override
    public Client updateClient(long id, Client client) {
        Optional<Client> cli = Clients.stream().filter(c -> c.getId() == id).findFirst();
        if(cli.isPresent()){
            int index = Clients.indexOf(cli.get());

            Client c = cli.get();
            c.setName(client.getName());
            c.setEmail(client.getEmail());
            c.setPhoneNumber(client.getPhoneNumber());
            c.setPassword(client.getPassword());

            Clients.set(index, c);

            return c;
        }
        return null;
    }

    @Override
    public Client getClient(long id) {
        Optional<Client> cli = Clients.stream().filter(c -> c.getId() == id).findFirst();
        if(cli.isPresent()){

            return cli.get();
        }
        return null;
    }

    @Override
    public List<Client> getClients() {
        return Clients;
    }

    @Override
    public void deleteClient(long id) {
        Clients.removeIf(c -> c.getId() == id);
    }
}
