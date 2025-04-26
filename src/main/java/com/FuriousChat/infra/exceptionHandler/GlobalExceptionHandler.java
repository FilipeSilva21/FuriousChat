package com.FuriousChat.infra.exceptionHandler;

import com.FuriousChat.infra.exceptionHandler.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound() {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createBody("Usuario com esse Id nao foi encontrado."));
    }

//    @ExceptionHandler(ServiceNotFoundException.class)
//    public ResponseEntity<Object> handleServiceNotFound(ServiceNotFoundException ex) {
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse servico nao foi encontrado.");
//    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFound() {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createBody("Usuario com esse email nao encontrado."));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials() {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Email ou senha inválidos.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception exception) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createBody("Erro interno: " + exception.getMessage()));
    }

    private Map<String, String> createBody(String message) {

        Map<String, String> error = new HashMap<>();

        error.put("error", message);

        return error;
    }
}
