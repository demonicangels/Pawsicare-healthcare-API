package com.example.pawsicare.persistence.jparepositories;

import com.example.pawsicare.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByEmail(String email);
    Optional<UserEntity> getUserEntityById(long id);

    @Modifying
    @Query("update UserEntity u set u.phoneNumber = coalesce(:phoneNumber, u.phoneNumber), u.email = coalesce(:email, u.email), u.password = coalesce(:pass,u.password) where  u.id = :id")
    void updateUserEntityById(@Param("id")Long id, @Param("email")String email, @Param("phoneNumber")String phoneNumber, @Param("pass")String pass);
    void deleteUserEntityById(long id);
    List<UserEntity> findByRole(Integer role);
}
