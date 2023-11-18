package com.example.pawsicare.domain.managerinterfaces;

import com.example.pawsicare.business.requests.loginUserRequest;
import com.example.pawsicare.business.responses.loginResponse;
import com.example.pawsicare.domain.user;

public interface loginService {
    loginResponse userLogin(loginUserRequest loginRequest);
}
