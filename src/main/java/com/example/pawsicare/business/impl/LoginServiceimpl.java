package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.business.dto.DoctorDTO;
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
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import com.example.pawsicare.persistence.UserEntityConverter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginServiceimpl implements LoginService {

    private final DoctorConverter doctorConverter;
    private final ClientConverter clientConverter;
    private final UserEntityConverter converter;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    /**
     * @return user when logged in
     * @should return a user when credentials are correct
     * @should return exception if credentials are not correct
     */
    public LoginResponse userLogin(LoginUserRequest loginRequest){
        String accessToken ="";


        Optional<User>  loggedInUser = Optional.ofNullable(userRepository.findUserEntityByEmail(loginRequest.getEmail()).map(converter :: fromUserEntity).orElse(null));

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

            accessToken = generateAccessToken(doctorConverter.fromDTO(doctorDTO.get()));

        } else if (loggedInUser.get() instanceof Client client) {
            client = (Client) loggedInUser.get();

            ClientDTO clientDTO = ClientDTO.builder()
                    .id(client.getId())
                    .name(client.getName())
                    .birthday(client.getBirthday())
                    .email(client.getEmail())
                    .phoneNumber(client.getPhoneNumber())
                    .role(client.getRole())
                    .build();

            accessToken = generateAccessToken(clientConverter.fromDTO(clientDTO));
        }

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
    public boolean passMatch(String rawPass, String encodedPass){
        return passwordEncoder.matches(rawPass, encodedPass);
    }

    /**
     * @param user
     * @return accessToken
     * @should return an accessToken based on the loggedIn user
     */
    public String generateAccessToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Long userId = user.getId();
        Role role = user.getRole();

        return accessTokenEncoder.encode(
                new AccessTokenImpl(userId, role));
    }
}
