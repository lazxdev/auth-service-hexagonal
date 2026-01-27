package dev.lazxdev.auth.infrastructure.controller;

import dev.lazxdev.auth.application.dto.AuthRequest;
import dev.lazxdev.auth.application.dto.RefreshTokenRequest;
import dev.lazxdev.auth.application.dto.RegisterResponse;
import dev.lazxdev.auth.application.dto.TokenResponse;
import dev.lazxdev.auth.application.port.in.LoginUseCase;
import dev.lazxdev.auth.application.port.in.RefreshTokenUseCase;
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
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    public AuthController(
            RegisterUserUseCase registerUserUseCase,
            LoginUseCase loginUseCase,
            RefreshTokenUseCase refreshTokenUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @Valid @RequestBody AuthRequest request) {

        TokenResponse tokenResponse = loginUseCase.login(request);

        return ResponseHandler.createResponse(
                HttpStatus.OK,
                "Successfully logged in",
                tokenResponse
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        TokenResponse tokenResponse = refreshTokenUseCase.refreshToken(request);

        return ResponseHandler.createResponse(
                HttpStatus.OK,
                "Token refreshed successfully",
                tokenResponse
        );
    }
}