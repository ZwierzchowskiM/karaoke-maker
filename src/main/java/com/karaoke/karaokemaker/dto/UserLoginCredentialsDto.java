package com.karaoke.karaokemaker.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserLoginCredentialsDto {

    private final String username;
    private final String password;

}

