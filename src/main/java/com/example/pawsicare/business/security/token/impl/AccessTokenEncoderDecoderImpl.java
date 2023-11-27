package com.example.pawsicare.business.security.token.impl;

import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.exception.InvalidAccessTokenException;
import com.example.pawsicare.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.pawsicare.business.security.token.AccessTokenDecoder;
import com.example.pawsicare.business.security.token.AccessTokenEncoder;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.parseLong;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenEncoder, AccessTokenDecoder {
    private final Key key;

    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (accessToken.getRole() != null){
            claimsMap.put("role", accessToken.getRole().name());
        }
        if (accessToken.getId() != null) {
            claimsMap.put("userId", accessToken.getId());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getId().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.SECONDS)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessTokenEncoded);
            Claims claims = jwt.getBody();

            String strRole = (String)claims.get("role");
            Role role = Role.valueOf(strRole);

            return new AccessTokenImpl(parseLong(claims.getSubject()), role);
        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }
}
