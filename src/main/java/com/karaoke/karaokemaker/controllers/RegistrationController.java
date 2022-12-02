package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.model.User;
import com.karaoke.karaokemaker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    String registrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration-form";
    }

    @PostMapping("/register")
    String register(User userRegistration) {
        userService.register(userRegistration);
        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    String registrationConfirmation() {
        return "registration-confirmation";
    }
}