package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.responses.JWTResponse;
import com.example.pawsicare.domain.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationService {
    JWTResponse loginUser(LoginUserRequest loginRequest);
    Boolean authenticateUser(Long userId);
}
