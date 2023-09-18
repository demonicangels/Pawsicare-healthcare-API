package com.example.PawsiCare.persistence;

import com.example.PawsiCare.persistence.DTOs.ClientDTO;

import java.util.List;

public interface ClientRepository {
    ClientDTO createClient(ClientDTO client);
    ClientDTO updateClient(long id, ClientDTO client);
    ClientDTO getClient(long id);
    List<ClientDTO> getClients();
    void deleteClient(long id);
}
