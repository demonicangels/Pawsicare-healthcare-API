package com.example.PawsiCare.persistence.jpaRepositories;

import com.example.PawsiCare.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
