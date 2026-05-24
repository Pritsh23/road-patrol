package com.roadpatrol.auth;

import com.roadpatrol.dto.*;
import com.roadpatrol.entity.User;
import com.roadpatrol.repository.UserRepository;
import com.roadpatrol.security.JWTService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequestDTO dto) {

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(
                        passwordEncoder.encode(dto.getPassword())
                )
                .build();

        userRepository.save(user);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean matches = passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword()
        );

        if (!matches) {
            throw new RuntimeException("Invalid password");
        }

        String token =
                jwtService.generateToken(user.getEmail());

        return AuthResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .role(
                        user.getRole() != null
                                ? user.getRole().name()
                                : "USER"
                )
                .build();
    }
}