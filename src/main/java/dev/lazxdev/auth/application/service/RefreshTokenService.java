package dev.lazxdev.auth.application.service;

import dev.lazxdev.auth.application.dto.RefreshTokenRequest;
import dev.lazxdev.auth.application.dto.TokenResponse;
import dev.lazxdev.auth.application.port.in.RefreshTokenUseCase;
import dev.lazxdev.auth.application.port.out.TokenPort;
import dev.lazxdev.auth.application.port.out.UserRepositoryPort;
import dev.lazxdev.auth.domain.model.User;
import dev.lazxdev.auth.infrastructure.exceptions.ApplicationErrorMessage;
import dev.lazxdev.auth.infrastructure.exceptions.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class RefreshTokenService implements RefreshTokenUseCase {

    private final TokenPort tokenPort;
    private final UserRepositoryPort userRepository;

    public RefreshTokenService(
            TokenPort tokenPort,
            UserRepositoryPort userRepository) {
        this.tokenPort = tokenPort;
        this.userRepository = userRepository;
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request){
        if (!tokenPort.validateRefreshToken(request.refreshToken())) {
            throw new ApplicationException(
                    ApplicationErrorMessage.INVALID_CREDENTIALS,
                    "Invalid or expired refresh token"
            );
        }

        String email = tokenPort.extractEmailFromToken(request.refreshToken());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(
                        ApplicationErrorMessage.USER_NOT_FOUND,
                        email
                ));

        if (!user.enabled()){
            throw new ApplicationException(ApplicationErrorMessage.ACCOUNT_DISABLED, user.email());
        }

        String newAccessToken = tokenPort.generateAccessToken(user);
        String newRefreshToken = tokenPort.generateRefreshToken(user);

        return new TokenResponse(
                newAccessToken,
                newRefreshToken,
                "Bearer",
                900000L, // 15 minutes in milliseconds
                LocalDateTime.now()
        );
    }
}
