package com.example.PawsiCare.Persistence;

import com.example.PawsiCare.Domain.Client;
import com.example.PawsiCare.Domain.Doctor;
import com.example.PawsiCare.Domain.Role;
import com.example.PawsiCare.Domain.User;
import com.example.PawsiCare.Domain.repositoryInterfaces.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@AllArgsConstructor
public class FakeUserRepositoryImpl implements UserRepository {


    private final List<User> users;
    public FakeUserRepositoryImpl(){
        this.users = new ArrayList<>();
            users.add(Doctor.builder()
                    .id(1)
                    .name("Maia")
                    .birthday("1-1-1999")
                    .password("123")
                    .email("maia@gmail.com")
                    .phoneNumber("+1234567")
                    .role(Role.Doctor)
                    .field("neurology")
                    .build());

            users.add(Client.builder()
                    .id(2)
                    .name("Maria")
                    .birthday("12-12-2000")
                    .password("123")
                    .email("maria@gmail.com")
                    .phoneNumber("+1234567")
                    .role(Role.Client)
                    .build());
    }

    @Override
    public Optional<User> loginUser(String email, String password) {

       var loggedInUser = users.stream().filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
               .findFirst();

        if(loggedInUser.isPresent() && loggedInUser.get().getRole().equals(Role.Doctor)){
            return loggedInUser;
        }
        return loggedInUser;

    }


}
