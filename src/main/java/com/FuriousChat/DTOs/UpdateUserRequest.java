package com.FuriousChat.DTOs;

import com.FuriousChat.models.enums.UserRole;

import java.time.LocalDate;

public record UpdateUserRequest(String firstName, String lastName, String username, LocalDate birthday, String email, String password, UserRole role) {
}
