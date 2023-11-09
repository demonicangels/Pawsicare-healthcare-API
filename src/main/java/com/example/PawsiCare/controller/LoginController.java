package com.example.PawsiCare.controller;

import com.example.PawsiCare.business.impl.LoginService;
import com.example.PawsiCare.domain.Client;
import com.example.PawsiCare.domain.Doctor;
import com.example.PawsiCare.domain.Role;
import com.example.PawsiCare.domain.User;
import com.example.PawsiCare.persistence.fakeRepositoryInterfaces.UserRepository;
import com.example.PawsiCare.business.requests.LoginUserRequest;
import com.example.PawsiCare.business.responses.GetUserResponse;
import com.example.PawsiCare.business.DTOs.ClientDTO;
import com.example.PawsiCare.business.DTOs.DoctorDTO;
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
public class LoginController {

    private final LoginService loginService;
    @PostMapping
    public ResponseEntity<GetUserResponse> userLogin(@RequestBody @Valid LoginUserRequest loginUserRequest) {

        Optional<User> loggedInUser = Optional.of(loginService.userLogin(loginUserRequest.getEmail(), loginUserRequest.getPassword()));

        try {
            if (loggedInUser.isPresent()) {
                if (loggedInUser.get() instanceof Doctor) {

                    Doctor doc = (Doctor) loggedInUser.get();

                    Optional<DoctorDTO> doctorDTO = Optional.of(DoctorDTO.builder()
                            .id(doc.getId())
                            .name(doc.getName())
                            .birthday(doc.getBirthday())
                            .email(doc.getEmail())
                            .phoneNumber(doc.getPhoneNumber())
                            .field(doc.getField())
                            .build());


                    GetUserResponse getUserResponse = GetUserResponse.builder()
                            .loggedInDoctor(doctorDTO)
                            .build();

                    return ResponseEntity.status(HttpStatus.OK).body(getUserResponse);
                } else if (loggedInUser.get() instanceof Client) {
                    Client cli = (Client) loggedInUser.get();

                    Optional<ClientDTO> clientDTO = Optional.of(ClientDTO.builder()
                            .id(cli.getId())
                            .name(cli.getName())
                            .birthday(cli.getBirthday())
                            .email(cli.getEmail())
                            .phoneNumber(cli.getPhoneNumber())
                            .build());

                    GetUserResponse getUserResponse = GetUserResponse.builder()
                            .loggedInClient(clientDTO)
                            .build();

                    return ResponseEntity.status(HttpStatus.OK).body(getUserResponse);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
