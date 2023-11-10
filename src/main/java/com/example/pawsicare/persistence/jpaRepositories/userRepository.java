package com.example.pawsicare.persistence.jpaRepositories;

import com.example.pawsicare.persistence.entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface userRepository extends JpaRepository<userEntity, Long> {
    Optional<userEntity> findUserEntityByEmailAndPassword(String email, String password);
    Optional<userEntity> getUserEntityById(long id);
    void deleteById(long id);
    List<userEntity> findByRole(Integer role);
}
