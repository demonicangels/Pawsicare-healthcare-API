package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.business.exceptions.UserNotAuthenticatedException;
import com.example.pawsicare.business.exceptions.UserNotFoundException;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long usrId);
    String encode (RefreshToken refreshToken);
    RefreshToken decode (String refreshToken);
    RefreshToken verifyExpiration(RefreshToken token);
    void clearRefreshToken(RefreshToken token);

    RefreshToken getRefreshTokenByUser(User user) throws UserNotFoundException;

    RefreshToken getRefreshTokenByToken(String accessToken) throws UserNotAuthenticatedException;
}
