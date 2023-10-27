package com.example.PawsiCare.Persistence;

import com.example.PawsiCare.Domain.Client;
import org.springframework.stereotype.Repository;
import com.example.PawsiCare.Domain.repositoryInterfaces.ClientRepository;

import java.util.*;

@Repository
public class FakeClientRepositoryImpl implements ClientRepository {
    private final List<Client> clients;
    int fakeID = 1;

    public FakeClientRepositoryImpl(){
        this.clients = new ArrayList<>();
    }
    @Override
    public Client createClient(Client client) {
        client.setId(fakeID);
        fakeID++;

        clients.add(client);
        return client;
    }

    @Override
    public Client updateClient(long id, Client client) {
        Optional<Client> cli = clients.stream().filter(c -> c.getId() == id).findFirst();
        if(cli.isPresent()){
            int index = clients.indexOf(cli.get());

            Client c = cli.get();
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
    public Client getClient(long id) {
        Optional<Client> cli = clients.stream().filter(c -> c.getId() == id).findFirst();
        if(cli.isPresent()){

            return cli.get();
        }
        return null;
    }

    @Override
    public List<Client> getClients() {
        return clients;
    }

    @Override
    public void deleteClient(long id) {
        clients.removeIf(c -> c.getId() == id);
    }
}
