package com.FuriousChat.DTOs;

import com.FuriousChat.models.enums.UserRole;

import java.time.LocalDate;

public record CreateUserRequest(String firstName, String lastName, String username, String cpf, LocalDate birthday, String email, String password, UserRole role) {
}
