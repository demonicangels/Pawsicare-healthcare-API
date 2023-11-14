package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.user;
import com.example.pawsicare.persistence.userEntityConverter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.pawsicare.persistence.jpaRepositories.userRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class loginService {

    private final userRepository userRepository;
    private final userEntityConverter converter;
    private final PasswordEncoder passwordEncoder;
    public user userLogin(String email, String rawPassword){

        Optional<user> loggedInUser = Optional.ofNullable(userRepository.findUserEntityByEmail(email).map(converter :: fromUserEntity).orElse(null));
        String encodedPass = passwordEncoder.encode(rawPassword);
        passwordEncoder.matches(loggedInUser.get().getPassword(), encodedPass);

        if(!loggedInUser.isEmpty()){
            return loggedInUser.get();
        }
        return null;
    }
}
