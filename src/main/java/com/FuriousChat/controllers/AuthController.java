package com.FuriousChat.controllers;

import com.FuriousChat.DTOs.CreateUserRequest;
import com.FuriousChat.DTOs.LoginRequest;
import com.FuriousChat.DTOs.TokenResponse;
import com.FuriousChat.infra.authConfig.TokenService;
import com.FuriousChat.infra.exceptionHandler.exceptions.EmailAlreadyExistsException;
import com.FuriousChat.models.User;
import com.FuriousChat.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid CreateUserRequest request) {

        try {
            if (userRepository.findByEmail(request.email()) != null)
                throw new EmailAlreadyExistsException("Usuário já cadastrado com esse email");

            String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());

            User newUser = new User(
                    null,
                    request.firstName(),
                    request.lastName(),
                    request.username(),
                    request.email(),
                    encryptedPassword,
                    request.birthday(),
                    request.cpf(),
                    request.role());

            userRepository.save(newUser);

            var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());

            var auth = authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new TokenResponse(token));
        } catch (EmailAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());

            var auth = authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new TokenResponse(token));
        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao fazer login: " + e.getMessage());
        }
    }
}

