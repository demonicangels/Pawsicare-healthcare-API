package com.example.pawsicare.business.impl;


import com.example.pawsicare.business.security.token.AccessTokenEncoder;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.RefreshTokenEntityConverter;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.jparepositories.RefreshTokenRepository;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRepository userRepository;
    private final UserEntityConverter userConverter;
    private final AccessTokenEncoder accessTokenEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenEntityConverter refreshTokenEntityConverter;

    @Override
    public RefreshToken createRefreshToken(Long usrId) {
        User user = userConverter.fromUserEntity(userRepository.getUserEntityById(usrId).get());

        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(user)
                .token(accessTokenEncoder.generateJWT(AccessTokenImpl.builder()
                        .userId(user.getId())
                        .role(user.getRole()).build())
                        .toString())
                .expiryDate(Date.from(Instant.now().plus(30, ChronoUnit.SECONDS)))
                .build();

        return refreshTokenEntityConverter.fromEntity(refreshTokenRepository.save(refreshTokenEntityConverter.toEntity(refreshToken)));
    }

    @Override
    public Optional<RefreshToken> getByToken(String token) {
        return Optional.of(refreshTokenEntityConverter.fromEntity(refreshTokenRepository.findByToken(token).get()));
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(token.getExpiryDate()) < 0){
            refreshTokenRepository.delete(refreshTokenEntityConverter.toEntity(token));
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}
