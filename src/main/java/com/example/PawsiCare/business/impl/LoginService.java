package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.domain.User;
import com.example.PawsiCare.persistence.fakeRepositoryInterfaces.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    public User userLogin(String email, String password){

        Optional<User> loggedInUser = userRepository.loginUser(email,password);
        if(!loggedInUser.isEmpty()){
            return loggedInUser.get();
        }
        return null;
    }
}
