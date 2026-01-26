package dev.lazxdev.auth.infrastructure.controller;

import dev.lazxdev.auth.application.dto.AuthRequest;
import dev.lazxdev.auth.application.dto.RegisterResponse;
import dev.lazxdev.auth.application.port.in.RegisterUserUseCase;
import dev.lazxdev.auth.infrastructure.controller.response.ApiResponse;
import dev.lazxdev.auth.infrastructure.controller.response.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody AuthRequest request) {

        RegisterResponse registerResponse = registerUserUseCase.register(request);

        return ResponseHandler.createResponse(
                HttpStatus.CREATED,
                "Successfully registered user",
                registerResponse
        );
    }
}