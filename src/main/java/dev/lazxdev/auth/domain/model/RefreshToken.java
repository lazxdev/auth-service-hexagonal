package dev.lazxdev.auth.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record RefreshToken(
        UUID id,
        String token,
        String email,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt,
        boolean revoked
) {

    public RefreshToken(
            String email,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt
    ) {
        this(
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                email,
                issuedAt,
                expiresAt,
                false
        );
    }

    public RefreshToken revoke() {
        return new RefreshToken(
                this.id,
                this.token,
                this.email,
                this.issuedAt,
                this.expiresAt,
                true
        );
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !revoked && !isExpired();
    }
}
