package dev.lazxdev.auth.application.port.in;

import dev.lazxdev.auth.application.dto.AuthRequest;
import dev.lazxdev.auth.application.dto.TokenResponse;

public interface LoginUseCase {
    TokenResponse login(AuthRequest request);
}
