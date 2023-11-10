package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.client;
import com.example.pawsicare.domain.managerinterfaces.clientManager;
import com.example.pawsicare.persistence.userEntityConverter;
import com.example.pawsicare.persistence.entity.clientEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class clientManagerImpl implements clientManager {
    private final com.example.pawsicare.persistence.jpaRepositories.userRepository userRepository;
    private userEntityConverter converter;

    @Override
    public client createClient(client client) {
        return converter.fromClientEntity(userRepository.save(converter.toClientEntity(client)));
    }

    @Override
    public client updateClient(client client) {
        return converter.fromClientEntity(userRepository.save(converter.toClientEntity(client)));
    }

    @Override
    public client getClient(long id) {
        return converter.fromClientEntity( userRepository.getUserEntityById(id).map(clientEntity.class :: cast).orElse(null));
    }

    @Override
    public List<client> getClients() {
        List<client> returnedClients = new ArrayList<>();
        userRepository.findAll().stream().map(converter :: fromUserEntity).map(u -> (client) u).forEach(
                returnedClients :: add
        );
        return returnedClients;
    }

    @Override
    public void deleteClient(long id) {
        userRepository.deleteById(id);
    }

}
