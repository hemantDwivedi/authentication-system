package com.assignment.authenticationsystem.service;

import com.assignment.authenticationsystem.dto.LoginDto;
import com.assignment.authenticationsystem.dto.UserDto;
import com.assignment.authenticationsystem.security.jwt.JwtAuthResponse;

public interface UserService {
    UserDto register(UserDto userDto);
    JwtAuthResponse login(LoginDto loginDto);
    void logout(String token);
}
