package com.FuriousChat.controllers;

import com.FuriousChat.DTOs.UpdateUserRequest;
import com.FuriousChat.infra.exceptionHandler.exceptions.UserNotFoundException;
import com.FuriousChat.models.enums.UserRole;
import com.FuriousChat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        try {
            var getAllUsers = userService.listUsers();

            return ResponseEntity.ok(getAllUsers);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar usuarios: " + e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @RequestBody UpdateUserRequest request, UserRole role) {

        try {
            userService.updateUser(userId, request, role);

            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar usuario: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteClient(@PathVariable("userId") Long userId) {

        try {
            userService.deleteUser(userId);

            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao excluir usuario: " + e.getMessage());
        }
    }
}
