package com.example.PawsiCare.persistence.jpaRepositories;

import com.example.PawsiCare.persistence.entity.DoctorEntity;
import com.example.PawsiCare.persistence.entity.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByEmailAndPassword(String email, String password);
    Optional<UserEntity> getUserEntityById(long id);
    void deleteById(long id);
    List<UserEntity> findByRole(Integer role);
}
