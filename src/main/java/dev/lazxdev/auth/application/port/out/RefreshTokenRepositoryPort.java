package dev.lazxdev.auth.application.port.out;

import dev.lazxdev.auth.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepositoryPort {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
    void revokeByEmail(String email);
    void revokeByToken(String token);
}
