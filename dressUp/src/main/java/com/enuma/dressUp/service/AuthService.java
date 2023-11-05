package com.enuma.dressUp.service;

import com.enuma.dressUp.dto.LoginDto;
import com.enuma.dressUp.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
