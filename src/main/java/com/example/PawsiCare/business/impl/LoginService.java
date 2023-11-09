package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.domain.User;
import com.example.PawsiCare.persistence.UserEntityConverter;
import com.example.PawsiCare.persistence.jpaRepositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final UserEntityConverter converter;
    public User userLogin(String email, String password){

        Optional<User> loggedInUser = Optional.ofNullable(converter.fromUserEntity(userRepository.findUserEntityByEmailAndPassword(email,password).get()));

        if(!loggedInUser.isEmpty()){
            return loggedInUser.get();
        }
        return null;
    }
}
