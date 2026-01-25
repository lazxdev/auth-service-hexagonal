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
}
