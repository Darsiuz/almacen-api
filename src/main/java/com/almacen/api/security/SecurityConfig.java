package com.almacen.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Publico
                        .requestMatchers("/auth/**").permitAll()

                        // PRODUCTOS (endpoint unico)
                        .requestMatchers(HttpMethod.GET, "/product", "/product/**")
                        .hasAnyRole("ADMIN", "MANAGER", "OPERATOR", "AUDITOR")

                        .requestMatchers(HttpMethod.POST, "/product", "/product/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.PUT, "/product/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.DELETE, "/product/**")
                        .hasRole("ADMIN")

                        // MOVIMIENTOS
                        .requestMatchers(HttpMethod.POST, "/movement/**")
                        .hasAnyRole("ADMIN", "OPERATOR")

                        .requestMatchers(HttpMethod.GET, "/movement/**")
                        .hasAnyRole("ADMIN", "MANAGER", "AUDITOR")

                        .requestMatchers(HttpMethod.PUT, "/movement/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/operator/**").hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers("/auditor/**").hasAnyRole("ADMIN", "AUDITOR")
                        // Cualquier otro endpoint
                        .anyRequest().authenticated())
                .httpBasic(httpBasic -> {
                });

        return http.build();
    }
}
