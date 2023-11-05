package com.enuma.dressUp.controller;

import com.enuma.dressUp.entity.User;
import com.enuma.dressUp.service.serviceImpl.UserServicesImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    private final UserServicesImpl userServices;

    public UserController(UserServicesImpl userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestBody User user) {
        try {
            userServices.registerUser(user);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            return "registration";
        }
    }

    @GetMapping("/login")
    public String showWelcomePage() {
        return "login";
    }


    @PostMapping("/login")
    public ResponseEntity<String> processLogin(@RequestParam("email") String email, @RequestParam("password") String password) {
        // Attempt to login the user
        Optional<User> optionalUser = userServices.loginUser(email, password);

        if (optionalUser.isPresent()) {
            // User is successfully authenticated
            return ResponseEntity.ok("User logged in successfully");
        } else {
            // User login failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }


    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}
