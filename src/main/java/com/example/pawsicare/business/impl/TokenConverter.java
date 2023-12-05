package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.UserRepository;

import java.util.Optional;

public class TokenConverter {

    UserRepository userRepository;
    UserEntityConverter userEntityConverter;
    public RefreshToken toRefreshToken (AccessTokenImpl token){
        Optional<UserEntity> userEntity = userRepository.getUserEntityById(token.getId());
        User user = null;

        if(userEntity.isPresent()){
            user = userEntityConverter.fromUserEntity(userEntity.get());
        }
        return RefreshToken.builder()
                .userInfo(user)
                .token(token.toString())
                .expiryDate(token.getExpiration())
                .build();
    }
}
