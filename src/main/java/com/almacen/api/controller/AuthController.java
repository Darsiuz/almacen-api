package com.almacen.api.controller;

import com.almacen.api.dto.LoginRequestDTO;
import com.almacen.api.dto.LoginResponseDTO;
import com.almacen.api.model.User;
import com.almacen.api.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.almacen.api.security.jwt.JwtService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final UserRepository userRepository;
        private final JwtService jwtService;

        public AuthController(AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        JwtService jwtService) {
                this.authenticationManager = authenticationManager;
                this.userRepository = userRepository;
                this.jwtService = jwtService;
        }

        @PostMapping("/login")
        public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
                String token = jwtService.generateToken(
                                user.getEmail(),
                                user.getRole().getName());
                return new LoginResponseDTO(
                                token,
                                user.getEmail(),
                                user.getRole().getName(),
                                user.getName());
        }
}
