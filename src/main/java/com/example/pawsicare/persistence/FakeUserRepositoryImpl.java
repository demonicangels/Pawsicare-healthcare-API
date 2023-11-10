package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.client;
import com.example.pawsicare.domain.doctor;
import com.example.pawsicare.domain.role;
import com.example.pawsicare.domain.user;
import com.example.pawsicare.persistence.fakeRepositoryInterfaces.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@AllArgsConstructor
public class FakeUserRepositoryImpl implements UserRepository {


    private final List<user> users;
    public FakeUserRepositoryImpl(){
        this.users = new ArrayList<>();
            users.add(doctor.builder()
                    .id(1L)
                    .name("Maia")
                    .birthday("1-1-1999")
                    .password("123")
                    .email("maia@gmail.com")
                    .phoneNumber("+1234567")
                    .role(role.Doctor)
                    .field("neurology")
                    .build());

            users.add(client.builder()
                    .id(2L)
                    .name("Maria")
                    .birthday("12-12-2000")
                    .password("123")
                    .email("maria@gmail.com")
                    .phoneNumber("+1234567")
                    .role(role.Client)
                    .build());
    }

    @Override
    public Optional<user> loginUser(String email, String password) {

       var loggedInUser = users.stream().filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
               .findFirst();

        if(loggedInUser.isPresent() && loggedInUser.get().getRole().equals(role.Doctor)){
            return loggedInUser;
        }
        return loggedInUser;

    }


}
