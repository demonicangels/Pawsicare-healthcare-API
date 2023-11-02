package com.example.PawsiCare.domain.jpaRepositories;

import com.example.PawsiCare.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
