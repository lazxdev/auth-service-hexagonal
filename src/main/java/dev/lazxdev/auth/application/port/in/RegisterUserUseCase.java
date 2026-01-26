package dev.lazxdev.auth.application.port.in;

import dev.lazxdev.auth.application.dto.AuthRequest;
import dev.lazxdev.auth.application.dto.RegisterResponse;

public interface RegisterUserUseCase {
    RegisterResponse register(AuthRequest request);
}
