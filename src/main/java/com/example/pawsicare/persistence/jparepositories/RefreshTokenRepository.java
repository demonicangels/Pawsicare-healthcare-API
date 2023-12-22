package com.example.pawsicare.persistence.jparepositories;

import com.example.pawsicare.persistence.entity.RefreshTokenEntity;
import com.example.pawsicare.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository  extends JpaRepository<RefreshTokenEntity,Long> {
    void deleteByUserInfo(UserEntity user);

    Optional<RefreshTokenEntity> findByUserInfo(UserEntity user);
}
