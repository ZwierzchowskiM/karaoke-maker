package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.dto.UserRegistrationDto;
import com.karaoke.karaokemaker.model.User;
import com.karaoke.karaokemaker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    ResponseEntity<User>  register(@RequestBody UserRegistrationDto registeredUser) {
        return ResponseEntity.ok(userService.register(registeredUser));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> putUser(@PathVariable Long id, @RequestBody UserRegistrationDto user) {
        return userService.replaceUser(id, user)
                .map(c -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok (userService.findUsers());
    }



}
