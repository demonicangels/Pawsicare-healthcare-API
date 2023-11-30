package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.jparepositories.UserRepository;

public class TokenConverter {

    UserRepository userRepository;
    UserEntityConverter userEntityConverter;
    public RefreshToken toRefreshToken (AccessTokenImpl token){
        return RefreshToken.builder()
                .userInfo(userEntityConverter.fromUserEntity(userRepository.getUserEntityById(token.getId()).get()))
                .token(token.toString())
                .expiryDate(token.getExpiration())
                .build();
    }
}
