package com.enuma.dressUp.controller;

import com.enuma.dressUp.dto.LoginDto;
import com.enuma.dressUp.dto.RegisterDto;
import com.enuma.dressUp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Build Login Rest API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }

    // build register rest API
    @PostMapping(value = {"/register", "/signup"})
    public  ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
         String response = authService.register(registerDto);
         return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
