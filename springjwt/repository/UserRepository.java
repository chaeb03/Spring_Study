package com.example.springjwt.repository;

import com.example.springjwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);
    UserEntity findByUserId(String userId);
}
