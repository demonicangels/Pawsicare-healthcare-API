package com.example.pawsicare.business.impl;


import com.example.pawsicare.business.security.token.AccessTokenEncoder;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.LoginService;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.RefreshTokenEntityConverter;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.RefreshTokenRepository;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

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
                .token(accessTokenEncoder.encode(new AccessTokenImpl(user.getId(),user.getRole())))
                .expiryDate(Instant.now().plusSeconds(10))
                .build();

        return refreshTokenRepository.save(refreshTokenEntityConverter.toEntity(refreshToken))
    }

    @Override
    public Optional<RefreshToken> getByToken(String token) {
        return Optional.empty();
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        return null;
    }
}
