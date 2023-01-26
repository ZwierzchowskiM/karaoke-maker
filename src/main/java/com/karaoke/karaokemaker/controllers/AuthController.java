package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.dto.UserLoginCredentialsDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @PostMapping("/login")
    public void login(@RequestBody UserLoginCredentialsDto credentials) {
    }



}
