package com.example.pawsicare.business.security.token;

import com.example.pawsicare.domain.Role;

public interface AccessToken {

    Long getId();

    Role getRole();

    boolean hasRole(String roleName);
}
