package com.example.PawsiCare.Controller;

import com.example.PawsiCare.Domain.Client;
import com.example.PawsiCare.Domain.Doctor;
import com.example.PawsiCare.Domain.Role;
import com.example.PawsiCare.Domain.User;
import com.example.PawsiCare.Domain.repositoryInterfaces.UserRepository;
import com.example.PawsiCare.Business.requests.LoginUserRequest;
import com.example.PawsiCare.Business.responses.GetUserResponse;
import com.example.PawsiCare.Business.DTOs.ClientDTO;
import com.example.PawsiCare.Business.DTOs.DoctorDTO;
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
public class UserController {

    private final UserRepository userRepository;
    @PostMapping
    public ResponseEntity<GetUserResponse> userLogin(@RequestBody @Valid LoginUserRequest loginUserRequest) {

        Optional<User> optionalUser = userRepository.loginUser(loginUserRequest.getEmail(), loginUserRequest.getPassword());

        try {
            if (optionalUser.isPresent()) {
                Role role = optionalUser.get().getRole();
                if (role == Role.Doctor) {
                    Doctor doc = (Doctor) optionalUser.get();

                    Optional<DoctorDTO> doctorDTO = Optional.ofNullable(DoctorDTO.builder()
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
                } else if (role == Role.Client) {
                    Client cli = (Client) optionalUser.get();

                    Optional<ClientDTO> clientDTO = Optional.ofNullable(ClientDTO.builder()
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
