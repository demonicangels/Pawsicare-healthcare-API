package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long usrId);
    Optional<RefreshToken> getByToken (String token);
    RefreshToken verifyExpiration(RefreshToken token);
}
