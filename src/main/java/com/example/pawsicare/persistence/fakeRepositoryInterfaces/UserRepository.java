package com.example.pawsicare.persistence.fakeRepositoryInterfaces;

import com.example.pawsicare.domain.user;

import java.util.Optional;

public interface UserRepository {
    Optional<user> loginUser(String email, String password);
}
