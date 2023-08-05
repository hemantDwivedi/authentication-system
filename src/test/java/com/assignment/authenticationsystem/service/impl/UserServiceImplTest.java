package com.assignment.authenticationsystem.service.impl;

import com.assignment.authenticationsystem.dto.UserDto;
import com.assignment.authenticationsystem.repository.UserRepository;
import com.assignment.authenticationsystem.token.TokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void registerTest(){
        var userDto = getUserDto();
        userService.register(userDto);
        var foundUser = userRepository.existsByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
        assertNotNull(foundUser);
    }



    private UserDto getUserDto(){
        return new UserDto(
                "testuser",
                "password",
                "testuser@gmail.com"
        );
    }
}