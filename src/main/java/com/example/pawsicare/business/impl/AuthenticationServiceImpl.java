package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.business.exceptions.InvalidCredentialsException;
import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.responses.JWTResponse;
import com.example.pawsicare.business.responses.LoginResponse;
import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.AccessTokenEncoder;
import com.example.pawsicare.business.security.token.impl.AccessTokenDecoderEncoderImpl;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.*;
import com.example.pawsicare.domain.managerinterfaces.AuthenticationService;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import com.example.pawsicare.persistence.UserEntityConverter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final DoctorConverter doctorConverter;
    private final ClientConverter clientConverter;
    private final UserEntityConverter converter;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    private final AccessTokenDecoderEncoderImpl accessTokenService;
    private final RefreshTokenService refreshTokenService;

    /**
     * @return user when logged in
     * @should return a user when credentials are correct
     * @should return exception if credentials are not correct
     * @should return doctor obj if a doctor is logged in
     * @should return client obj if a client is logged in
     */
    @Override
    public LoginResponse loginUser(LoginUserRequest loginRequest){
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

    @Override
    public Boolean authenticateUser(Long usrId) {
        User user = converter.fromUserEntity(userRepository.getUserEntityById(usrId).get());

        if(user != null){
          return true;
        }
        return false;
    }

    public boolean passMatch(String rawPass, String encodedPass){
        return passwordEncoder.matches(rawPass, encodedPass);
    }

    /**
     * @param user
     * @return accessToken
     * @should return an accessToken based on the loggedIn user
     * @should return IllegalArgument exception when user is null
     */
    public String generateAccessToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Long userId = user.getId();
        Role role = user.getRole();

        return accessTokenEncoder.generateAccessToken(
                new AccessTokenImpl(userId, role));
    }

    @Override
    public JWTResponse authenticateAndGetToken (@RequestBody LoginUserRequest request){ //for when a refreshToken is needed

        AccessToken accessToken = accessTokenService.decode(loginUser(request).getAccessToken());
        if(accessToken != null){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(accessToken.getId());
            return JWTResponse.builder()
                    .refreshToken(refreshToken.getToken())
                    .accessToken(accessToken.toString()).build();
        }
        else {
            throw new InvalidCredentialsException();
        }
    }
}
