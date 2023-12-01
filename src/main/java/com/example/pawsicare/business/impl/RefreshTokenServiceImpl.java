package com.example.pawsicare.business.impl;



import com.example.pawsicare.business.security.token.AccessTokenEncoder;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.RefreshToken;
import com.example.pawsicare.domain.Role;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.domain.managerinterfaces.RefreshTokenService;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.parseLong;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRepository userRepository;
    private final UserEntityConverter userConverter;
    private final AccessTokenEncoder accessTokenEncoder;

    private final Key key;

    public RefreshTokenServiceImpl(@Value("${jwt.secret}") String secretKey,
                                   UserRepository userRepository,
                                   UserEntityConverter userEntityConverter,
                                   AccessTokenEncoder accessTokenEncoder) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userRepository = userRepository;
        this.userConverter = userEntityConverter;
        this.accessTokenEncoder = accessTokenEncoder;
    }

    @Override
    public RefreshToken createRefreshToken(Long usrId) {
        User user = userConverter.fromUserEntity(userRepository.getUserEntityById(usrId).get());


        return RefreshToken.builder()
                .userInfo(user)
                .token(accessTokenEncoder.generateJWT(AccessTokenImpl.builder()
                        .userId(user.getId())
                        .role(user.getRole()).build()))
                .expiryDate(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .build();
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(token.getExpiryDate()) < 0){
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
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
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        // Extract information from JWT claims
        Long userId = parseLong(claims.get("userId").toString());
        Role role = Role.valueOf(claims.get("role", String.class));

        User user = Client.builder()
                .id(userId)
                .role(role).build();

        return RefreshToken.builder()
                .userInfo(user)
                .token(refreshToken)
                .expiryDate(claims.getExpiration())
                .build();
    }
}
