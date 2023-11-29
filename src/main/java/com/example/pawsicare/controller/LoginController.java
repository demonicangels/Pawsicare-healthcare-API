package com.example.pawsicare.controller;

import com.example.pawsicare.business.exceptions.InvalidCredentialsException;
import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.requests.RefreshTokenRequest;
import com.example.pawsicare.business.responses.JWTResponse;
import com.example.pawsicare.business.responses.LoginResponse;
import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.business.security.token.impl.AccessTokenDecoderEncoderImpl;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.managerinterfaces.AuthenticationService;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.RefreshTokenEntityConverter;
import com.example.pawsicare.persistence.jparepositories.RefreshTokenRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class LoginController {

    private final AuthenticationService authenticationService;
    private final AccessTokenDecoderEncoderImpl accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenEntityConverter converter;

    @PostMapping
    public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        try{

            LoginResponse response = authenticationService.loginUser(loginUserRequest);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new InvalidCredentialsException();
        }
    }

    @PostMapping("/refreshToken")
    public JWTResponse refreshToken (@RequestBody RefreshTokenRequest request) {
        return refreshTokenRepository.findByToken(request.getAccessToken())
                .map(converter::fromEntity)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo).map(userInfo -> {
                    Boolean isUserAuthenticated = authenticationService.authenticateUser(userInfo.getId());
                    String accessToken = "";
                    if (isUserAuthenticated) {
                        accessToken = accessTokenService.generateAccessToken(accessTokenService.decode(request.getAccessToken()));
                    }
                    return JWTResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(request.getAccessToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
