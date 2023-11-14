package com.example.pawsicare.persistence.jpaRepositories;

import com.example.pawsicare.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface userRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByEmailAndPassword(String email, String password);
    Optional<UserEntity> getUserEntityById(long id);
    void deleteById(long id);
    List<UserEntity> findByRole(Integer role);
}
