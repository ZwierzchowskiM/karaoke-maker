package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.model.User;
import com.karaoke.karaokemaker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userform")
    String home(Model model) {
        model.addAttribute("user", new User());
        return "userform";
    }

//        @PostMapping("/register")
//        String register(@Valid User user, BindingResult bindingResult) {
//            if (bindingResult.hasErrors()) {
//                return "userform";
//            } else {
//                userService.addUser(user);
//                return "redirect:success";
//            }
//        }

        @GetMapping("/success")
        String success() {
            return "redirect:/";
        }

}
