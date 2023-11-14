package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.managerinterfaces.clientManager;
import com.example.pawsicare.persistence.userEntityConverter;
import com.example.pawsicare.persistence.entity.ClientEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class clientManagerImpl implements clientManager {
    private final com.example.pawsicare.persistence.jpaRepositories.userRepository userRepository;
    private userEntityConverter converter;

    @Override
    public Client createClient(Client client) {
        return converter.fromClientEntity(userRepository.save(converter.toClientEntity(client)));
    }

    @Override
    public Client updateClient(Client client) {
        return converter.fromClientEntity(userRepository.save(converter.toClientEntity(client)));
    }

    @Override
    public Client getClient(long id) {
        return converter.fromClientEntity( userRepository.getUserEntityById(id).map(ClientEntity.class :: cast).orElse(null));
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
