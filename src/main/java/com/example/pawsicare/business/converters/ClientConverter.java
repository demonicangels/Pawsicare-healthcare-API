package com.example.pawsicare.business.converters;

import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.domain.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientConverter {

    public ClientDTO toDTO (Client client){

        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .birthday(client.getBirthday())
                .password(client.getPassword())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .role(client.getRole())
                .build();
    }

    public Client fromDTO (ClientDTO clientDTO){

        return Client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .birthday(clientDTO.getBirthday())
                .password(clientDTO.getPassword())
                .email(clientDTO.getEmail())
                .phoneNumber(clientDTO.getPhoneNumber())
                .role(clientDTO.getRole())
                .build();
    }
}
