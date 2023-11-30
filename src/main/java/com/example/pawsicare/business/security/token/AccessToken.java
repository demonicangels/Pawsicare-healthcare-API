package com.example.pawsicare.business.security.token;

import com.example.pawsicare.domain.Role;

import java.util.Date;

public interface AccessToken {

    Long getId();

    Role getRole();

    Date getExpiration();

    boolean hasRole(String roleName);
}
