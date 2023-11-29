package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.responses.JWTResponse;
import com.example.pawsicare.business.responses.LoginResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationService {
    LoginResponse loginUser(LoginUserRequest loginRequest);
    Boolean authenticateUser(Long usrId);
    JWTResponse authenticateAndGetToken (@RequestBody LoginUserRequest request);
}
