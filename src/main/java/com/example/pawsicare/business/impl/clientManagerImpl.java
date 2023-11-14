package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.client;
import com.example.pawsicare.domain.managerinterfaces.clientManager;
import com.example.pawsicare.persistence.userEntityConverter;
import com.example.pawsicare.persistence.entity.clientEntity;
import com.example.pawsicare.persistence.jpaRepositories.userRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class clientManagerImpl implements clientManager {
    private final  userRepository userRepository;
    private final userEntityConverter converter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public client createClient(client client) {
        String encodedPass = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPass);
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
