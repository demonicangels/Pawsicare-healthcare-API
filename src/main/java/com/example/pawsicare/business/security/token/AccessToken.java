package com.example.pawsicare.business.security.token;

import java.util.Set;

public interface AccessToken {
    String getEmail();

    Long getId();

    Set<String> getRoles();

    boolean hasRole(String roleName);
}
