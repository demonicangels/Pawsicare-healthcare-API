package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.domain.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long usrId);
    String encode (RefreshToken refreshToken);
    RefreshToken decode (String refreshToken);
    RefreshToken verifyExpiration(RefreshToken token);
    void clearRefreshToken(RefreshToken token);
}
