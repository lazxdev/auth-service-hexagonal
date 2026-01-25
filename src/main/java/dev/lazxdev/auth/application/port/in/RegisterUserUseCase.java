package dev.lazxdev.auth.application.port.in;

import dev.lazxdev.auth.application.dto.RegisterRequest;
import dev.lazxdev.auth.application.dto.UserResponse;

public interface RegisterUserUseCase {
    UserResponse register(RegisterRequest request);
}
