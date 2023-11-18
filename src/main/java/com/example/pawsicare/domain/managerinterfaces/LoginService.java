package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.responses.LoginResponse;

public interface LoginService {
    LoginResponse userLogin(LoginUserRequest loginRequest);
}
