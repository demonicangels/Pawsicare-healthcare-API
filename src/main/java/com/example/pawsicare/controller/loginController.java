package com.example.pawsicare.controller;

import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.business.requests.loginUserRequest;
import com.example.pawsicare.business.responses.getUserResponse;
import com.example.pawsicare.business.DTOs.ClientDTO;
import com.example.pawsicare.business.DTOs.DoctorDTO;
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

    private final com.example.pawsicare.business.impl.loginService loginService;
    @PostMapping
    public ResponseEntity<getUserResponse> userLogin(@RequestBody @Valid loginUserRequest loginUserRequest) {

        Optional<User> loggedInUser = Optional.of(loginService.userLogin(loginUserRequest.getEmail(), loginUserRequest.getPassword()));

        try {
                if (loggedInUser.get() instanceof Doctor doc) {

                    doc = (Doctor) loggedInUser.get();

                    Optional<DoctorDTO> doctorDTO = Optional.of(DoctorDTO.builder()
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
                } else if (loggedInUser.get() instanceof Client client) {
                     client = (Client) loggedInUser.get();

                    Optional<ClientDTO> clientDTO = Optional.of(ClientDTO.builder()
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
