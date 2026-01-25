package dev.lazxdev.auth.application.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        Set<String> roles,
        boolean enabled,
        LocalDateTime createdAt
) {
}
