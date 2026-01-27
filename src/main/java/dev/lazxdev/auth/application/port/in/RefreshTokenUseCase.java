package dev.lazxdev.auth.application.port.in;

import dev.lazxdev.auth.application.dto.RefreshTokenRequest;
import dev.lazxdev.auth.application.dto.TokenResponse;

public interface RefreshTokenUseCase {
    TokenResponse refreshToken(RefreshTokenRequest request);
}
