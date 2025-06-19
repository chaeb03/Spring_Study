package com.example.springjwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class JoinDTO {

    private String userId;
    private String password;
    private String email;
    private String gender;
    private LocalDate joinDate;
}
