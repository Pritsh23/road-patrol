package com.roadpatrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import com.roadpatrol.security.JWTFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JWTFilter jwtFilter;

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

    // PUBLIC
    .requestMatchers("/api/v1/auth/**")
    .permitAll()

    // USER
    .requestMatchers(
            "/api/v1/issues",
            "/api/v1/issues/my-issues",
            "/api/v1/votes/**"
    )
    .hasRole("USER")

    // AUTHORITY
    .requestMatchers(
            "/api/v1/issues/*/status"
    )
    .hasRole("AUTHORITY")

    // ADMIN
    .requestMatchers(
            "/api/v1/dashboard/**",
            "/api/v1/escalations/**"
    )
    .hasRole("ADMIN")

    .anyRequest()
    .authenticated()
)
            // Add the filter right here
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}
