package com.example.pawsicare.controller;

import com.example.pawsicare.business.requests.loginUserRequest;
import com.example.pawsicare.business.responses.getUserResponse;
import com.example.pawsicare.business.responses.loginResponse;
import com.example.pawsicare.domain.managerinterfaces.loginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class loginController {

    private final loginService loginService;
    @PostMapping
    public ResponseEntity<loginResponse> userLogin(@RequestBody @Valid loginUserRequest loginUserRequest) {

        try{
            loginResponse response = loginService.userLogin(loginUserRequest);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw e;
        }
    }
}
