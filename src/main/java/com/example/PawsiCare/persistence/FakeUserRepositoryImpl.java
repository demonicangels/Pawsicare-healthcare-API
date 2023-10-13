package com.example.PawsiCare.persistence;

import com.example.PawsiCare.business.domain.Client;
import com.example.PawsiCare.business.domain.Doctor;
import com.example.PawsiCare.business.domain.Role;
import com.example.PawsiCare.business.domain.User;
import com.example.PawsiCare.business.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeUserRepositoryImpl implements UserRepository {

    List<User> users = new ArrayList<>() {{
        add(Doctor.builder()
                .id(1)
                .name("Maia")
                .birthday("1-1-1999")
                .password("123")
                .email("maia@gmail.com")
                .phoneNumber("+1234567")
                .role(Role.Doctor)
                .field("neurology")
                .build());

        add(Client.builder()
                .id(2)
                .name("Maria")
                .birthday("12-12-2000")
                .password("123")
                .email("maria@gmail.com")
                .phoneNumber("+1234567")
                .role(Role.Client)
                .build());
    }};
    @Override
    public Optional<User> loginUser(String email, String password) {

       var loggedInUser = users.stream().filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
               .findFirst();

        if(loggedInUser.isPresent() && loggedInUser.get().getRole().equals(Role.Doctor)){
            Optional<User> doc = loggedInUser;

            return doc;
        }
        else if(loggedInUser.isPresent() && loggedInUser.get().getRole().equals(Role.Client)){
            Optional<User> cli = Optional.ofNullable(loggedInUser.get());

            return cli;
        }
        return loggedInUser;

    }


}
