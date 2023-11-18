package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.DTOs.ClientDTO;
import com.example.pawsicare.business.DTOs.DoctorDTO;
import com.example.pawsicare.business.exceptions.InvalidCredentialsException;
import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.responses.LoginResponse;
import com.example.pawsicare.business.security.token.AccessTokenEncoder;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.Role;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.LoginService;
import com.example.pawsicare.persistence.jpaRepositories.UserRepository;
import com.example.pawsicare.persistence.UserEntityConverter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginServiceimpl implements LoginService {

    private final UserRepository userRepository;
    private final UserEntityConverter converter;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    public LoginResponse userLogin(LoginUserRequest loginRequest){

        Optional<User> loggedInUser = Optional.ofNullable(userRepository.findUserEntityByEmail(loginRequest.getEmail()).map(converter :: fromUserEntity).orElse(null));

        if(loggedInUser.isEmpty()){
            throw new InvalidCredentialsException();
        }
        if(!passMatch(loginRequest.getPassword(), loggedInUser.get().getPassword())){
            throw new InvalidCredentialsException();
        }
        if (loggedInUser.get() instanceof Doctor doc) {

            doc = (Doctor) loggedInUser.get();

            Optional<DoctorDTO> doctorDTO = Optional.of(DoctorDTO.builder()
                    .id(doc.getId())
                    .name(doc.getName())
                    .birthday(doc.getBirthday())
                    .email(doc.getEmail())
                    .phoneNumber(doc.getPhoneNumber())
                    .field(doc.getField())
                    .build());
        } else if (loggedInUser.get() instanceof Client client) {
            client = (com.example.pawsicare.domain.Client) loggedInUser.get();

            Optional<ClientDTO> clientDTOOptional = Optional.of(ClientDTO.builder()
                    .id(client.getId())
                    .name(client.getName())
                    .birthday(client.getBirthday())
                    .email(client.getEmail())
                    .phoneNumber(client.getPhoneNumber())
                    .build());
        }

        String accessToken = generateAccessToken(loggedInUser.get());
        LoginResponse.builder().accessToken(accessToken);
        return new LoginResponse(accessToken);
    }
    private boolean passMatch(String rawPass, String encodedPass){
        return passwordEncoder.matches(rawPass, encodedPass);
    }

    private String generateAccessToken(User user) {
        Long userId = user.getId() != null ? user.getId() : null;
        List<String> roles = Arrays.stream(Role.values())
                .map(Enum::name).toList();

        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getId().toString(), userId, roles));
    }
}
