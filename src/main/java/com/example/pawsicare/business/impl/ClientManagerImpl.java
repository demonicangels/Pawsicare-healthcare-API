package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.managerinterfaces.ClientManager;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.converters.UserEntityConverter;
import com.example.pawsicare.persistence.entity.ClientEntity;
import com.example.pawsicare.persistence.entity.UserEntity;
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
    private final UserEntityConverter userEntityConverter;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Override
    public Client createClient(Client client) {
        String encodedPass = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPass);

        return userEntityConverter.fromClientEntity(userRepository.save(userEntityConverter.toClientEntity(client)));
    }

    @Override
    public Client updateClient(Client client) {
        Optional<UserEntity> userEntity = userRepository.getUserEntityById(client.getId());

        if(!userEntity.isEmpty()){
            ClientEntity clientEntity = (ClientEntity) userEntity.get();

            if(client.getEmail() == null || client.getEmail().isEmpty()){
                client.setEmail(clientEntity.getEmail());
            }
            if(client.getPhoneNumber() == null || client.getPhoneNumber().isEmpty()){
                client.setPhoneNumber(clientEntity.getPhoneNumber());
            }

            if(client.getPassword() == null || client.getPassword().isEmpty()){
                client.setPassword(clientEntity.getPassword());
            }else{
                String encodedPass = passwordEncoder.encode(client.getPassword());
                client.setPassword(encodedPass);
            }
        }
        userRepository.updateUserEntityById(client.getId(), client.getEmail(), client.getPhoneNumber(), client.getPassword());

        return getClient(client.getId());
    }

    @Override
    public Client getClient(long id) {
        return userEntityConverter.fromClientEntity( userRepository.getUserEntityById(id).map(ClientEntity.class :: cast).orElse(null));
    }

    @Override
    public List<Client> getClients() {
        List<Client> returnedClients = new ArrayList<>();
        userRepository.findByRole(0).stream().map(userEntityConverter :: fromUserEntity).map(u -> (Client) u).forEach(
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
