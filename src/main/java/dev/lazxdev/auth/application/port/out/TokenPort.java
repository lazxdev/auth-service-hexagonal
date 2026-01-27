package dev.lazxdev.auth.application.port.out;

import dev.lazxdev.auth.domain.model.User;

public interface TokenPort {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    boolean validateAccessToken(String token);
    boolean validateRefreshToken(String token);
    String extractEmailFromToken(String token);
    String extractTokenIdFromToken(String token);
}
