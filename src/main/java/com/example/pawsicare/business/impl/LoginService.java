package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.User;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.jpaRepositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final UserEntityConverter converter;
    public User userLogin(String email, String password){

        Optional<User> loggedInUser = Optional.ofNullable(userRepository.findUserEntityByEmailAndPassword(email,password).map(converter :: fromUserEntity).orElse(null));

        if(!loggedInUser.isEmpty()){
            return loggedInUser.get();
        }
        return null;
    }
}
