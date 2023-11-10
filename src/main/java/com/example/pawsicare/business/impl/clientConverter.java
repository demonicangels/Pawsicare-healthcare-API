package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.DTOs.clientDTO;
import com.example.pawsicare.domain.client;
import org.springframework.stereotype.Service;

@Service
public class clientConverter {

    public clientDTO toDTO (client client){

        return clientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .birthday(client.getBirthday())
                .password(client.getPassword())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    public client fromDTO (clientDTO clientDTO){

        return client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .birthday(clientDTO.getBirthday())
                .password(clientDTO.getPassword())
                .email(clientDTO.getEmail())
                .phoneNumber(clientDTO.getPhoneNumber())
                .build();
    }
}
