package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.business.DTOs.ClientDTO;
import com.example.PawsiCare.domain.Client;
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
                .build();
    }
}
