package com.example.pawsicare.controller;

import com.example.pawsicare.business.DTOs.clientDTO;
import com.example.pawsicare.business.DTOs.doctorDTO;
import com.example.pawsicare.business.requests.loginUserRequest;
import com.example.pawsicare.business.responses.getUserResponse;
import com.example.pawsicare.domain.client;
import com.example.pawsicare.domain.doctor;
import com.example.pawsicare.domain.managerinterfaces.loginService;
import com.example.pawsicare.domain.user;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class loginController {

    private final loginService loginService;
    @PostMapping
    public ResponseEntity<getUserResponse> userLogin(@RequestBody @Valid loginUserRequest loginUserRequest) {

        Optional<user> loggedInUser = Optional.of(loginService.userLogin(loginUserRequest));

        try {
                if (loggedInUser.get() instanceof doctor doc) {

                    doc = (doctor) loggedInUser.get();

                    Optional<doctorDTO> doctorDTO = Optional.of(com.example.pawsicare.business.DTOs.doctorDTO.builder()
                            .id(doc.getId())
                            .name(doc.getName())
                            .birthday(doc.getBirthday())
                            .email(doc.getEmail())
                            .phoneNumber(doc.getPhoneNumber())
                            .field(doc.getField())
                            .build());


                    getUserResponse getUserResponse = com.example.pawsicare.business.responses.getUserResponse.builder()
                            .loggedInDoctor(doctorDTO)
                            .build();

                    return ResponseEntity.status(HttpStatus.OK).body(getUserResponse);
                } else if (loggedInUser.get() instanceof client client) {
                     client = (com.example.pawsicare.domain.client) loggedInUser.get();

                    Optional<clientDTO> clientDTO = Optional.of(com.example.pawsicare.business.DTOs.clientDTO.builder()
                            .id(client.getId())
                            .name(client.getName())
                            .birthday(client.getBirthday())
                            .email(client.getEmail())
                            .phoneNumber(client.getPhoneNumber())
                            .build());

                    getUserResponse getUserResponse = com.example.pawsicare.business.responses.getUserResponse.builder()
                            .loggedInClient(clientDTO)
                            .build();

                    return ResponseEntity.status(HttpStatus.OK).body(getUserResponse);
                }
        } catch (Exception e) {
            throw e;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
