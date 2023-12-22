package com.example.pawsicare.controller;

import com.example.pawsicare.business.exceptions.InvalidCredentialsException;
import com.example.pawsicare.business.exceptions.RefreshTokenExpiredException;
import com.example.pawsicare.business.exceptions.UserNotAuthenticatedException;
import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.requests.RefreshTokenRequest;
import com.example.pawsicare.business.responses.JWTResponse;
import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.impl.AccessTokenDecoderEncoderImpl;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.AuthenticationService;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.RefreshTokenEntityConverter;
import com.example.pawsicare.persistence.entity.RefreshTokenEntity;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.RefreshTokenRepository;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AccessTokenDecoderEncoderImpl accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenEntityConverter refreshTokenEntityConverter;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<JWTResponse> loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        try{

            JWTResponse response = authenticationService.loginUser(loginUserRequest);

            return ResponseEntity.ok(response);


        } catch (Exception e) {
            throw new InvalidCredentialsException();
        }
    }

    @PostMapping("/refreshToken")
    public JWTResponse refreshToken (@RequestBody RefreshTokenRequest token) throws UserNotAuthenticatedException {

        AccessToken access = accessTokenService.decode(token.getToken());

        UserEntity user = userRepository.getUserEntityById(access.getId()).get();

        Optional<RefreshTokenEntity> refreshTFromDb = refreshTokenRepository.findByUserInfo(user);

        if (refreshTFromDb.isEmpty()) {
            throw new UserNotAuthenticatedException("Refresh token not found");
        }
        RefreshToken refreshToken = refreshTokenEntityConverter.fromEntity(refreshTFromDb.get());

        RefreshToken notExpiredRefreshToken = refreshTokenService.verifyExpiration(refreshToken);

        if(notExpiredRefreshToken != null){
            User userInfo = refreshToken.getUserInfo();
            Boolean isUserAuthenticated = authenticationService.authenticateUser(userInfo.getId());

            // Generate a new access token
            if (isUserAuthenticated) {

                AccessTokenImpl accessToken = AccessTokenImpl.builder()
                        .userId(userInfo.getId())
                        .role(userInfo.getRole()).build();

                // Generate a new access token using the information from the existing access token
                String newAccessToken = accessTokenService.generateJWT(accessToken);

                return JWTResponse.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(refreshToken.getToken())
                        .build();
            } else {
                throw new UserNotAuthenticatedException("User not authenticated");
            }
        }else {
            throw new RefreshTokenExpiredException("Refresh token has expired");
        }
    }

    @PostMapping("/logout")
    public void deleteOldRefreshToken(@RequestBody RefreshTokenRequest request){
        RefreshToken refreshToken = refreshTokenService.decode(request.getToken());
        refreshTokenService.clearRefreshToken(refreshToken);
    }
}
