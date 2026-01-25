package dev.lazxdev.auth.domain.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record User(
        UUID id,
        String email,
        String password,
        RoleType role,
        boolean enabled,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public User(
            String email,
            String password,
            RoleType role,
            boolean enabled,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        this(
            UUID.randomUUID(),
            email,
            password,
            role,
            enabled,
            createdAt,
            updatedAt
        );
    }

    public User(
            UUID id,
            String email,
            String password,
            RoleType role,
            boolean enabled,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
