package com.example.pawsicare.business.security.token;

import com.example.pawsicare.domain.Role;

import java.util.Set;

public interface AccessToken {

    Long getId();

    Role getRole();

    boolean hasRole(String roleName);
}
