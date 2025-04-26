package com.FuriousChat.models.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    admin("admin"),
    common("common");

    private final String role;

    UserRole (String role) {
        this.role = role;
    }
}
