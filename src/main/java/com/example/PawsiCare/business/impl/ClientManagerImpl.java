package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.domain.Client;
import com.example.PawsiCare.domain.managerInterfaces.ClientManager;
import com.example.PawsiCare.persistence.UserEntityConverter;
import com.example.PawsiCare.persistence.entity.ClientEntity;
import com.example.PawsiCare.persistence.jpaRepositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ClientManagerImpl implements ClientManager {
    private final UserRepository userRepository;
    private UserEntityConverter converter;

    @Override
    public Client createClient(Client client) {
        return converter.fromClientEntity(userRepository.save(converter.toClientEntity(client)));
    }

    @Override
    public Client updateClient( Client client) {
        return converter.fromClientEntity(userRepository.save(converter.toClientEntity(client)));
    }

    @Override
    public Client getClient(long id) {
        return converter.fromClientEntity((ClientEntity) userRepository.getUserEntityById(id).get());
    }

    @Override
    public List<Client> getClients() {
        List<Client> returnedClients = new ArrayList<>();
        userRepository.findAll().stream().map(converter :: fromUserEntity).map(u -> (Client) u).forEach(
                returnedClients :: add
        );
        return returnedClients;
    }

    @Override
    public void deleteClient(long id) {
        userRepository.deleteById(id);
    }

}
