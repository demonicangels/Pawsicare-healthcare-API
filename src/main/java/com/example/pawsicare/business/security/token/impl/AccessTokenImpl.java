package com.example.pawsicare.business.security.token.impl;

import com.example.pawsicare.business.security.token.AccessToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String email;
    private final Long userId;
    private final Set<String> roles;

    public AccessTokenImpl(String email, Long userId, Collection<String> roles) {
        this.email = email;
        this.userId = userId;
        this.roles = roles != null ? Set.copyOf(roles) : Collections.emptySet();
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public boolean hasRole(String roleName) {
        return this.roles.contains(roleName);
    }
}
