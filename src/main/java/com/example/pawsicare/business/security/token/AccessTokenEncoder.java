package com.example.pawsicare.business.security.token;

public interface AccessTokenEncoder {
    String generateJWT(AccessToken accessToken);
}
