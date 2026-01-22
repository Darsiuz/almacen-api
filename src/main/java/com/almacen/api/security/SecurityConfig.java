package com.almacen.api.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // CLAVE

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())

                // MANEJO DE ERRORES DE SEGURIDAD
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.setContentType("application/json");

                            String message = "Credenciales incorrectas";

                            if (authException instanceof org.springframework.security.authentication.DisabledException) {
                                message = "El usuario esta desactivado";
                            }

                            response.getWriter().write("""
                                        {
                                          "status": 401,
                                          "error": "Unauthorized",
                                          "message": "%s"
                                        }
                                    """.formatted(message));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(403);
                            response.setContentType("application/json");
                            response.getWriter().write("""
                                        {
                                          "status": 403,
                                          "error": "Forbidden",
                                          "message": "No tiene permisos para ENTRAR"
                                        }
                                    """);
                        }))

                // AUTORIZACION
                .authorizeHttpRequests(auth -> auth

                        // Publico
                        .requestMatchers("/auth/**").permitAll()

                        // PRODUCTOS
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

                        // INCIDENCIAS
                        .requestMatchers(HttpMethod.POST, "/incident/**")
                        .hasAnyRole("ADMIN", "OPERATOR")

                        .requestMatchers(HttpMethod.GET, "/incident/**")
                        .hasAnyRole("ADMIN", "MANAGER", "AUDITOR")

                        .requestMatchers(HttpMethod.PUT, "/incident/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // REPORTES
                        .requestMatchers(HttpMethod.GET, "/report/**")
                        .hasAnyRole("ADMIN", "MANAGER", "AUDITOR")

                        // TEST ROLES
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/operator/**").hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers("/auditor/**").hasAnyRole("ADMIN", "AUDITOR")

                        .anyRequest().authenticated())

                // BASIC AUTH
                .httpBasic(httpBasic -> {
                });

        return http.build();
    }
}
