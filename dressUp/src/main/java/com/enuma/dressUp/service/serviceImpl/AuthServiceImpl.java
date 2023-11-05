package com.enuma.dressUp.service.serviceImpl;

import com.enuma.dressUp.dto.LoginDto;
import com.enuma.dressUp.dto.RegisterDto;
import com.enuma.dressUp.entity.Role;
import com.enuma.dressUp.entity.User;
import com.enuma.dressUp.exception.BlogApiException;
import com.enuma.dressUp.repository.RoleRepository;
import com.enuma.dressUp.repository.UserRepository;
import com.enuma.dressUp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        return "User Logged-in successfully!.";
    }

    @Override
    public String register(RegisterDto registerDto) {

        // add check for username exists in database
        if(userRepository.existsByUserName((registerDto.getUsername()))) {
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Username already exists!!");
        }
        // add check for email exists in database

        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Email already exists!!");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setUserName(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!.";
    }

}
