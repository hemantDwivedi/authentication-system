package com.assignment.authenticationsystem.service.impl;

import com.assignment.authenticationsystem.dto.LoginDto;
import com.assignment.authenticationsystem.dto.UserDto;
import com.assignment.authenticationsystem.exception.ApiException;
import com.assignment.authenticationsystem.exception.ResourceNotFoundException;
import com.assignment.authenticationsystem.model.Enum.ERole;
import com.assignment.authenticationsystem.model.Role;
import com.assignment.authenticationsystem.model.User;
import com.assignment.authenticationsystem.repository.RoleRepository;
import com.assignment.authenticationsystem.repository.UserRepository;
import com.assignment.authenticationsystem.security.jwt.JwtAuthResponse;
import com.assignment.authenticationsystem.security.jwt.JwtTokenProvider;
import com.assignment.authenticationsystem.service.UserService;
import com.assignment.authenticationsystem.token.Token;
import com.assignment.authenticationsystem.token.TokenRepository;
import com.assignment.authenticationsystem.token.TokenType;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private TokenRepository tokenRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    @Override
    public UserDto register(UserDto userDto) {
        if (userRepository.existsByUsernameOrEmail(userDto.getUsername(), userDto.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "User already exists. Try different Username Or Email");
        }
        Set<Role> roles = new HashSet<>();
        if (roleRepository.findByName(ERole.ROLE_USER.toString()).isEmpty()) {
            Role role = new Role();
            role.setName(ERole.ROLE_USER.toString());
            roleRepository.save(role);
        }
        roles.add(roleRepository.findByName(ERole.ROLE_USER.toString()).orElseThrow(() -> new ResourceNotFoundException("Role not exists")));
        User user = modelMapper.map(userDto, User.class);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateToken(authentication);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(jwtToken);
        return jwtAuthResponse;
    }

    @Override
    public void logout(String bearerToken) {
        String jwtToken = bearerToken.substring(7);
        var username = jwtTokenProvider.getUsername(jwtToken);
        var user = userRepository.findByUsernameOrEmail(username, username).orElse(null);
        if(tokenRepository.findByToken(jwtToken).orElse(null) == null){
        var token = savedUserToken(user, jwtToken);
        tokenRepository.save(token);
        }
    }

    private static Token savedUserToken(User user, String jwtToken) {
        return Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .build();
    }
}
