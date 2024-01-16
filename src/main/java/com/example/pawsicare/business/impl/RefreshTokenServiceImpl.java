package com.example.pawsicare.business.impl;


import com.example.pawsicare.business.exceptions.RefreshTokenExpiredException;
import com.example.pawsicare.business.exceptions.UnauthorizedUserException;
import com.example.pawsicare.business.exceptions.UserNotAuthenticatedException;
import com.example.pawsicare.business.exceptions.UserNotFoundException;
import com.example.pawsicare.business.security.token.impl.AccessTokenDecoderEncoderImpl;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.*;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.converters.RefreshTokenEntityConverter;
import com.example.pawsicare.persistence.converters.UserEntityConverter;
import com.example.pawsicare.persistence.entity.RefreshTokenEntity;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.RefreshTokenRepository;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.Long.parseLong;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRepository userRepository;
    private final UserEntityConverter userConverter;
    private final AccessTokenDecoderEncoderImpl accessTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenEntityConverter refreshTokenEntityConverter;
    private final Key key;

    Dotenv dotenv = Dotenv.configure()
            .filename("jwtSecretKey.env")
            .load();
    String obtainedSecretKey = dotenv.get("jwt.secret");

    public RefreshTokenServiceImpl(UserRepository userRepository,
                                   UserEntityConverter userEntityConverter,
                                   AccessTokenDecoderEncoderImpl accessTokenEncoder,
                                   RefreshTokenRepository refreshTokenRepository,
                                   RefreshTokenEntityConverter refreshTokenEntityConverter) {
        byte[] keyBytes = Decoders.BASE64.decode(obtainedSecretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userRepository = userRepository;
        this.userConverter = userEntityConverter;
        this.accessTokenService = accessTokenEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenEntityConverter = refreshTokenEntityConverter;
    }

    @Override
    public RefreshToken createRefreshToken(Long usrId) {

        Optional<UserEntity> userEntity = userRepository.getUserEntityById(usrId);

        if(userEntity.isPresent()){
            User user = userConverter.fromUserEntity(userEntity.get());

            return RefreshToken.builder()
                    .userInfo(user)
                    .token(accessTokenService.generateJWT(AccessTokenImpl.builder()
                            .userId(user.getId())
                            .role(user.getRole()).build()))
                    .expiryDate(Date.from(Instant.now().plus(32, ChronoUnit.HOURS)))
                    .build();
        }
        else{
            throw new UnauthorizedUserException();
        }
    }
    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(token.getExpiryDate()) < 0){
            refreshTokenRepository.deleteByUserInfo(refreshTokenEntityConverter.toEntity(token).getUserInfo());
            throw new RefreshTokenExpiredException(" Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

    @Transactional
    @Override
    public void clearRefreshToken(RefreshToken token) {

            UserEntity userInfo = refreshTokenEntityConverter.toEntity(token).getUserInfo();
            if(userInfo != null){
                refreshTokenRepository.deleteByUserInfo(userInfo);
            }
    }

    @Override
    public RefreshToken getRefreshTokenByUser(User user) throws UserNotFoundException {
        Optional<RefreshTokenEntity> refreshTokenEntityOptional = refreshTokenRepository.findByUserInfo(userConverter.toUserEntity(user));

        if (!refreshTokenEntityOptional.isEmpty()) {
            RefreshTokenEntity refreshTokenEntity = refreshTokenEntityOptional.get();
            return refreshTokenEntityConverter.fromEntity(refreshTokenEntity);
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public RefreshToken getRefreshTokenByToken(String accessToken) throws UserNotAuthenticatedException {
        Optional<RefreshTokenEntity> refreshTokenEntityOptional = refreshTokenRepository.findByToken(accessToken);
        if(!refreshTokenEntityOptional.isEmpty()){
            return refreshTokenEntityConverter.fromEntity(refreshTokenEntityOptional.get());
        }
        throw new UserNotAuthenticatedException("User not authenticated!");
    }

    @Override
    public String encode (RefreshToken refreshToken) { //do one for the refresh token
        Map<String, Object> claimsMap = new HashMap<>();

        if (refreshToken.getUserInfo().getRole() != null){
            claimsMap.put("role", refreshToken.getUserInfo().getRole().name());
        }
        if (refreshToken.getUserInfo().getId() != null) {
            claimsMap.put("userId", refreshToken.getUserInfo().getId());
        }

        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(refreshToken.getUserInfo().getId().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(refreshToken.getExpiryDate())
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public RefreshToken decode (String refreshToken){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .setAllowedClockSkewSeconds(300)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        // Extract information from JWT claims
        Long userId = parseLong(claims.get("userId").toString());
        Role role = Role.valueOf(claims.get("role", String.class));
        User user;

        if(role.name().equals("Client")){
            user = Client.builder()
                    .id(userId)
                    .role(role).build();
        }
        else{
            user = Doctor.builder()
                    .id(userId)
                    .role(role).build();
        }


        return RefreshToken.builder()
                .userInfo(user)
                .token(refreshToken)
                .expiryDate(claims.getExpiration())
                .build();
    }
}
