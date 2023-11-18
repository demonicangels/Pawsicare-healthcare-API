package com.example.pawsicare.business.security.token;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
