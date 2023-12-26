package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.responses.JWTResponse;

public interface AuthenticationService {
    JWTResponse loginUser(LoginUserRequest loginRequest);
    Boolean authenticateUser(Long userId);
}
