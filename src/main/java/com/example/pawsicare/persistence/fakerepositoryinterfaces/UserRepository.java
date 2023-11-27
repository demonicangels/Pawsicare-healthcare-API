package com.example.pawsicare.persistence.fakerepositoryinterfaces;

import com.example.pawsicare.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> loginUser(String email, String password);
}
