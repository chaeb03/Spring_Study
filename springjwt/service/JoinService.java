package com.example.springjwt.service;

import com.example.springjwt.dto.JoinDTO;
import com.example.springjwt.entity.UserEntity;
import com.example.springjwt.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {
        String userId = joinDTO.getUserId();

        if (userRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        user.setEmail(joinDTO.getEmail());
        user.setGender(joinDTO.getGender());
        user.setJoinDate(joinDTO.getJoinDate() != null ?
                joinDTO.getJoinDate() : LocalDate.now());
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }
}