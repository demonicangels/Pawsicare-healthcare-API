package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.converters.ClientConverter;
import com.example.pawsicare.business.converters.DoctorConverter;
import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.business.exceptions.InvalidCredentialsException;
import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.responses.JWTResponse;
import com.example.pawsicare.business.security.token.impl.AccessTokenDecoderEncoderImpl;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.*;
import com.example.pawsicare.domain.managerinterfaces.AuthenticationService;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.converters.RefreshTokenEntityConverter;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.RefreshTokenRepository;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import com.example.pawsicare.persistence.converters.UserEntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final DoctorConverter doctorConverter;
    private final ClientConverter clientConverter;
    private final UserEntityConverter userEntityConverter;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenDecoderEncoderImpl accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenEntityConverter refreshTokenEntityConverter;

    /**
     * @return user when logged in
     * @should return a user when credentials are correct
     * @should return exception if credentials are not correct
     * @should return doctor obj if a doctor is logged in
     * @should return client obj if a client is logged in
     */
    @Override
    public JWTResponse loginUser(LoginUserRequest loginRequest){
        String accessToken ="";
        RefreshToken refreshToken = null;
        String refresh = "";

        Optional<User>  loggedInUser = Optional.ofNullable(userRepository.findUserEntityByEmail(loginRequest.getEmail()).map(userEntityConverter :: fromUserEntity).orElse(null));

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
                    .role(doc.getRole())
                    .build());

            Doctor doctor = doctorConverter.fromDTO(doctorDTO.get());

            accessToken = generateAccessToken(doctor);

            refreshToken = refreshTokenService.createRefreshToken(doctor.getId());

            refreshTokenRepository.save(refreshTokenEntityConverter.toEntity(refreshToken));

            refresh = refreshTokenService.encode(refreshToken);


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

            Client client1 =  clientConverter.fromDTO(clientDTO);

            accessToken = generateAccessToken(client1);

            refreshToken = refreshTokenService.createRefreshToken(client1.getId());

            refreshTokenRepository.save(refreshTokenEntityConverter.toEntity(refreshToken));

            refresh = refreshTokenService.encode(refreshToken);

        }

        return JWTResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refresh)
                .build();
    }

    /**
     * @param usrId
     * @should return true if a user with the same id is found in the database
     * @should return false if no user with that id is found in the db
     * @return true if user found in db
     * @return false if no user is found
     */
    @Override
    public Boolean authenticateUser(Long usrId) {
        Optional<UserEntity> userEntity = userRepository.getUserEntityById(usrId);

        return userEntity.isPresent();
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

        return accessTokenService.generateJWT(
                AccessTokenImpl.builder()
                         .userId(userId)
                         .role(role).build());
    }
}
