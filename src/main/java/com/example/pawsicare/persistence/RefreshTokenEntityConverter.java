package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.persistence.entity.RefreshTokenEntity;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenEntityConverter {

    UserEntityConverter userConverter;
    public RefreshToken fromEntity (RefreshTokenEntity entity){
        return RefreshToken.builder()
                .userInfo(userConverter.fromUserEntity(entity.getUserInfo()))
                .token(entity.getToken())
                .expiryDate(entity.getExpiryDate())
                .build();
    }

    public RefreshTokenEntity toEntity (RefreshToken token){
        return RefreshTokenEntity.builder()
                .userInfo(userConverter.toUserEntity(token.getUserInfo()))
                .token(token.getToken())
                .expiryDate(token.getExpiryDate())
                .build();
    }
}
