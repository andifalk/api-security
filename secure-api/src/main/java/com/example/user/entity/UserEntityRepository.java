package com.example.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Deprecated
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

}
