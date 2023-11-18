package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.DTOs.clientDTO;
import com.example.pawsicare.business.DTOs.doctorDTO;
import com.example.pawsicare.business.exceptions.InvalidCredentialsException;
import com.example.pawsicare.business.requests.loginUserRequest;
import com.example.pawsicare.business.responses.loginResponse;
import com.example.pawsicare.business.security.token.AccessTokenEncoder;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.client;
import com.example.pawsicare.domain.doctor;
import com.example.pawsicare.domain.managerinterfaces.loginService;
import com.example.pawsicare.domain.role;
import com.example.pawsicare.domain.user;
import com.example.pawsicare.persistence.userEntityConverter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.pawsicare.persistence.jpaRepositories.userRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class loginServiceimpl implements loginService {

    private final userRepository userRepository;
    private final userEntityConverter converter;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    public loginResponse userLogin(loginUserRequest loginRequest){

        Optional<user> loggedInUser = Optional.ofNullable(userRepository.findUserEntityByEmail(loginRequest.getEmail()).map(converter :: fromUserEntity).orElse(null));

        if(loggedInUser.isEmpty()){
            throw new InvalidCredentialsException();
        }
        if(!passMatch(loginRequest.getPassword(), loggedInUser.get().getPassword())){
            throw new InvalidCredentialsException();
        }
        if (loggedInUser.get() instanceof doctor doc) {

            doc = (doctor) loggedInUser.get();

            Optional<doctorDTO> doctorDTO = Optional.of(com.example.pawsicare.business.DTOs.doctorDTO.builder()
                    .id(doc.getId())
                    .name(doc.getName())
                    .birthday(doc.getBirthday())
                    .email(doc.getEmail())
                    .phoneNumber(doc.getPhoneNumber())
                    .field(doc.getField())
                    .build());
        } else if (loggedInUser.get() instanceof client client) {
            client = (com.example.pawsicare.domain.client) loggedInUser.get();

            Optional<clientDTO> clientDTOOptional = Optional.of(com.example.pawsicare.business.DTOs.clientDTO.builder()
                    .id(client.getId())
                    .name(client.getName())
                    .birthday(client.getBirthday())
                    .email(client.getEmail())
                    .phoneNumber(client.getPhoneNumber())
                    .build());
        }

        String accessToken = generateAccessToken(loggedInUser.get());
        loginResponse.builder().accessToken(accessToken);
        return new loginResponse(accessToken);
    }
    private boolean passMatch(String rawPass, String encodedPass){
        return passwordEncoder.matches(rawPass, encodedPass);
    }

    private String generateAccessToken(user user) {
        Long userId = user.getId() != null ? user.getId() : null;
        List<String> roles = Arrays.stream(role.values())
                .map(Enum::name).toList();

        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getId().toString(), userId, roles));
    }
}
