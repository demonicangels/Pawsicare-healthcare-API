package com.example.PawsiCare.domain.repositoryInterfaces;

import com.example.PawsiCare.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> loginUser(String email, String password);
}