package com.example.pawsicare.business.security.token.impl;

import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.domain.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {

    private final Long userId;
    private final Role role;

    public AccessTokenImpl(Long userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public boolean hasRole(String roleName) {
        return this.role.name().equals(roleName);
    }
}
