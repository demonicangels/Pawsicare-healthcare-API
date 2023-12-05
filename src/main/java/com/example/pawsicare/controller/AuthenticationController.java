package com.example.pawsicare.controller;

import com.example.pawsicare.business.exceptions.AccessTokenGenerationException;
import com.example.pawsicare.business.exceptions.InvalidCredentialsException;
import com.example.pawsicare.business.exceptions.UserNotAuthenticatedException;
import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.requests.RefreshTokenRequest;
import com.example.pawsicare.business.responses.JWTResponse;
import com.example.pawsicare.business.security.token.impl.AccessTokenDecoderEncoderImpl;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.AuthenticationService;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.RefreshTokenEntityConverter;
import com.example.pawsicare.persistence.jparepositories.RefreshTokenRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AccessTokenDecoderEncoderImpl accessTokenService;
    private final RefreshTokenService refreshTokenService;

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
    public JWTResponse refreshToken (@RequestBody @NotNull RefreshTokenRequest request) throws UserNotAuthenticatedException, AccessTokenGenerationException {
        RefreshToken refreshToken = refreshTokenService.decode(request.getRefreshToken());
        RefreshToken verifiedToken = refreshTokenService.verifyExpiration(refreshToken);

        User userInfo = verifiedToken.getUserInfo();

        Boolean isUserAuthenticated = authenticationService.authenticateUser(userInfo.getId());

        String accessToken;
        if (isUserAuthenticated) {
            accessToken = accessTokenService.generateJWT(accessTokenService.decode(request.getRefreshToken()));
        } else {
            throw new UserNotAuthenticatedException("USER_NOT_FOUND.INVALID_REFRESH_TOKEN");
        }

        if (accessToken.isEmpty()) {
            throw new AccessTokenGenerationException("Access token generation failed");
        }



        return JWTResponse.builder()
                .accessToken(accessToken)
                .refreshToken(request.getRefreshToken())
                .build();
    }
}
