package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.dto.UserRegistrationDto;
import com.karaoke.karaokemaker.exceptions.ResourceNotFoundException;
import com.karaoke.karaokemaker.model.User;
import com.karaoke.karaokemaker.repositories.UserRepository;
import com.karaoke.karaokemaker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("${api.path}/users")
public class UserController {

    UserService userService;
    UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getUser(@PathVariable Long id) {

        User user = userService.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song with ID :" + id + " Not Found"));

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok (userService.findUsers());
    }


    @PostMapping("/")
    ResponseEntity<User>  registerUser(@RequestBody UserRegistrationDto registeredUser) {
        return ResponseEntity.ok(userService.register(registeredUser));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {

        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User with ID :" + id + " Not Found");
        }

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRegistrationDto user) {

        User modifiedUser = userService.replaceUser(id, user)
        .orElseThrow(() -> new ResourceNotFoundException("User with ID :" + id + " Not Found"));

        return ResponseEntity.ok().body(modifiedUser);
    }
}
