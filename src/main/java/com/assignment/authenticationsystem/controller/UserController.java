package com.assignment.authenticationsystem.controller;

import com.assignment.authenticationsystem.dto.LoginDto;
import com.assignment.authenticationsystem.dto.UserDto;
import com.assignment.authenticationsystem.security.jwt.JwtAuthResponse;
import com.assignment.authenticationsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserDto userDto){
        return new ResponseEntity<>(userService.register(userDto), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String bearerToken){
        userService.logout(bearerToken);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("Admin Panel");
    }
}
