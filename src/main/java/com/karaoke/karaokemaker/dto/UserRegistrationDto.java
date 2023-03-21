package com.karaoke.karaokemaker.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserRegistrationDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

}