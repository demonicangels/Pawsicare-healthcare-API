package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.managerinterfaces.ClientManager;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.entity.ClientEntity;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ClientManagerImpl implements ClientManager {
    private final  UserRepository userRepository;
    private final UserEntityConverter converter;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Override
    public Client createClient(Client client) {
        String encodedPass = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPass);

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
        userRepository.findByRole(0).stream().map(converter :: fromUserEntity).map(u -> (Client) u).forEach(
                returnedClients :: add
        );
        return returnedClients;
    }

    @Transactional
    @Override
    public void deleteUser(long id, RefreshToken token) {
        refreshTokenService.clearRefreshToken(token);
        userRepository.deleteUserEntityById(id);
    }

}
