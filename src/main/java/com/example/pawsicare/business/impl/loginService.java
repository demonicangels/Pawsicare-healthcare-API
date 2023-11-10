package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.user;
import com.example.pawsicare.persistence.userEntityConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class loginService {

    private final com.example.pawsicare.persistence.jpaRepositories.userRepository userRepository;
    private final userEntityConverter converter;
    public user userLogin(String email, String password){

        Optional<user> loggedInUser = Optional.ofNullable(userRepository.findUserEntityByEmailAndPassword(email,password).map(converter :: fromUserEntity).orElse(null));

        if(!loggedInUser.isEmpty()){
            return loggedInUser.get();
        }
        return null;
    }
}
