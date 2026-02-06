package dev.lazxdev.auth.application.port.out;

import dev.lazxdev.auth.domain.model.User;

public interface TokenPort {
    String generateAccessToken(User user);
    boolean validateAccessToken(String token);
    String extractEmailFromToken(String token);
    String extractUserIdFromToken(String token);
}
