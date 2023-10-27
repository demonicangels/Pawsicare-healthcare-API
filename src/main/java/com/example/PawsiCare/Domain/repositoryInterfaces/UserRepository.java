package com.example.PawsiCare.Domain.repositoryInterfaces;

import com.example.PawsiCare.Domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> loginUser(String email, String password);
}
