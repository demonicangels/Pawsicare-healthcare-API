package com.example.pawsicare.controller;

import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.responses.LoginResponse;
import com.example.pawsicare.domain.managerinterfaces.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class LoginController {

    private final LoginService loginService;
    @PostMapping
    public ResponseEntity<LoginResponse> userLogin(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        try{
            LoginResponse response = loginService.userLogin(loginUserRequest);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw e;
        }
    }
}
