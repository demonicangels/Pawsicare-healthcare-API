package com.example.pawsicare.controller;

import com.example.pawsicare.business.exceptions.InvalidCredentialsException;
import com.example.pawsicare.business.exceptions.RefreshTokenExpiredException;
import com.example.pawsicare.business.exceptions.UserNotAuthenticatedException;
import com.example.pawsicare.business.exceptions.UserNotFoundException;
import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.requests.RefreshTokenRequest;
import com.example.pawsicare.business.responses.JWTResponse;
import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.impl.AccessTokenDecoderEncoderImpl;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.Role;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.AuthenticationService;
import com.example.pawsicare.domain.managerinterfaces.ClientManager;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.converters.UserEntityConverter;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AccessTokenDecoderEncoderImpl accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final ClientManager clientManager;
    private final UserEntityConverter userEntityConverter;

    private String errorMsg = "User not allowed!";

    @PostMapping
    public ResponseEntity<JWTResponse> loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        try{

            JWTResponse response = authenticationService.loginUser(loginUserRequest);

            return ResponseEntity.ok(response);


        }catch (Exception e) {
            throw new InvalidCredentialsException();
        }
    }

    @PostMapping("/refreshToken")
    public JWTResponse refreshToken (@RequestBody RefreshTokenRequest token) throws UserNotAuthenticatedException, UserNotFoundException {

        AccessToken access = accessTokenService.decode(token.getToken());

        Optional<UserEntity> optionalUser = userRepository.getUserEntityById(access.getId());

        if(!optionalUser.isEmpty()) {

            RefreshToken refreshTFromDb = refreshTokenService.getRefreshTokenByUser(userEntityConverter.fromUserEntity(optionalUser.get()));

            RefreshToken refreshToken = refreshTFromDb;

            RefreshToken notExpiredRefreshToken = refreshTokenService.verifyExpiration(refreshToken);

            if (notExpiredRefreshToken != null) {
                User userInfo = refreshToken.getUserInfo();
                Boolean isUserAuthenticated = authenticationService.authenticateUser(userInfo.getId());

                // Generate a new access token
                if (isUserAuthenticated.equals(true)) {

                    AccessTokenImpl accessToken = AccessTokenImpl.builder()
                            .userId(userInfo.getId())
                            .role(userInfo.getRole()).build();

                    // Generate a new access token using the information from the existing access token
                    String newAccessToken = accessTokenService.generateJWT(accessToken);

                    return JWTResponse.builder()
                            .accessToken(newAccessToken)
                            .refreshToken(refreshToken.getToken())
                            .build();
                }

            } else {
                throw new RefreshTokenExpiredException("Refresh token has expired");
            }
        }
        throw new UserNotAuthenticatedException("User not found");
    }

    @PostMapping("/logout")
    public void deleteOldRefreshToken(@RequestBody RefreshTokenRequest request){
        RefreshToken refreshToken = refreshTokenService.decode(request.getToken());
        refreshTokenService.clearRefreshToken(refreshToken);
    }

    @RolesAllowed({"Client", "Doctor"})
    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@RequestParam(name = "id") long id, @RequestParam(name = "token")String token) throws UserNotAuthenticatedException {
        AccessToken tokenClaims = accessTokenService.decode(token);

        RefreshToken refreshToken = refreshTokenService.getRefreshTokenByToken(token);

        Long userId = tokenClaims.getId();
        boolean isClient = tokenClaims.hasRole(Role.Client.name());
        boolean isDoctor = tokenClaims.hasRole(Role.Doctor.name());

        if(userId.equals(id) && (isClient || isDoctor) ){
            clientManager.deleteUser(id, refreshToken);
            return ResponseEntity.noContent().build();
        }
        throw new UserNotAuthenticatedException(errorMsg);
    }
}
