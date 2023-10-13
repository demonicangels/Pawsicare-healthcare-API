package com.example.PawsiCare.business.repositories;

import com.example.PawsiCare.business.domain.Client;
import com.example.PawsiCare.business.domain.Doctor;
import com.example.PawsiCare.business.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> loginUser(String email, String password);
}
