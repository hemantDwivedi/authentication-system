package com.assignment.authenticationsystem.service.impl;

import com.assignment.authenticationsystem.dto.LoginDto;
import com.assignment.authenticationsystem.dto.UserDto;
import com.assignment.authenticationsystem.model.Enum.ERole;
import com.assignment.authenticationsystem.model.Role;
import com.assignment.authenticationsystem.model.User;
import com.assignment.authenticationsystem.repository.RoleRepository;
import com.assignment.authenticationsystem.repository.UserRepository;
import com.assignment.authenticationsystem.security.jwt.JwtAuthResponse;
import com.assignment.authenticationsystem.security.jwt.JwtTokenProvider;
import com.assignment.authenticationsystem.token.Token;
import com.assignment.authenticationsystem.token.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtTokenProvider jwtTokenProvider;
    @Mock private RoleRepository roleRepository;
    @Mock private TokenRepository tokenRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_NewUser_Success(){
        var userDto = getUserDto();
        Role role = new Role();
        role.setName(ERole.ROLE_USER.toString());
        when(userRepository.existsByUsernameOrEmail(any(), any())).thenReturn(false);
        when(roleRepository.findByName(any())).thenReturn(Optional.of(role));
        when(modelMapper.map(any(UserDto.class), eq(User.class))).thenReturn(new User());
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);

        var result = userService.register(userDto);

        assertNotNull(result);
        assertEquals(userDto.getUsername(),result.getUsername());
    }


    @Test
    public void testLogin_ValidCredentials_Success() {
        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("testuser");
        loginDto.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        String jwtToken = "mocked-jwt-token";
        when(jwtTokenProvider.generateToken(any(Authentication.class))).thenReturn(jwtToken);

        // Act
        JwtAuthResponse result = userService.login(loginDto);

        // Assert
        assertNotNull(result);
        assertEquals(jwtToken, result.getAccessToken());
    }

    @Test
    public void testLogout_ValidBearerToken_Success() {
        String bearerToken = "Bearer mock-jwt-token";

        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");

        String jwtToken = "mock-jwt-token";
        when(jwtTokenProvider.getUsername(eq(jwtToken))).thenReturn("testuser");
        when(userRepository.findByUsernameOrEmail(eq("testuser"), eq("testuser"))).thenReturn(Optional.of(user));
        when(tokenRepository.findByToken(eq(jwtToken))).thenReturn(Optional.empty());

        userService.logout(bearerToken);


        verify(tokenRepository).save(any(Token.class));
    }

    private UserDto getUserDto(){
        return new UserDto(
                "testuser",
                "password",
                "testuser@gmail.com"
        );
    }
}