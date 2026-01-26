package dev.lazxdev.auth.application.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record RegisterResponse(
        String email,
        Set<String> roles,
        boolean enabled,
        LocalDateTime createdAt
) {
}
