package dev.lazxdev.auth.application.service;

import dev.lazxdev.auth.application.dto.RefreshTokenRequest;
import dev.lazxdev.auth.application.dto.TokenResponse;
import dev.lazxdev.auth.application.port.in.RefreshTokenUseCase;
import dev.lazxdev.auth.application.port.out.RefreshTokenRepositoryPort;
import dev.lazxdev.auth.application.port.out.TokenPort;
import dev.lazxdev.auth.application.port.out.UserRepositoryPort;
import dev.lazxdev.auth.domain.model.RefreshToken;
import dev.lazxdev.auth.domain.model.User;
import dev.lazxdev.auth.infrastructure.exceptions.ApplicationErrorMessage;
import dev.lazxdev.auth.infrastructure.exceptions.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class RefreshTokenService implements RefreshTokenUseCase {

    private final TokenPort tokenPort;
    private final UserRepositoryPort userRepository;
    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;

    public RefreshTokenService(
            TokenPort tokenPort,
            UserRepositoryPort userRepository, RefreshTokenRepositoryPort refreshTokenRepositoryPort) {
        this.tokenPort = tokenPort;
        this.userRepository = userRepository;
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request){

        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepositoryPort
                .findByToken(request.refreshToken());

        if (refreshTokenOpt.isEmpty()){
            throw new ApplicationException(
                    ApplicationErrorMessage.INVALID_CREDENTIALS,
                    "Invalid refresh Token"
            );
        }

        RefreshToken refreshToken = refreshTokenOpt.get();

        if (refreshToken.revoked()){
            throw new ApplicationException(
                    ApplicationErrorMessage.INVALID_CREDENTIALS,
                    "Refresh token revoked"
            );
        }

        if (refreshToken.isExpired()){
            throw new ApplicationException(
                    ApplicationErrorMessage.INVALID_CREDENTIALS,
                    "Refresh token expired"
            );
        }

        User user = userRepository.findByEmail(refreshToken.email())
                .orElseThrow(()-> new ApplicationException(
                        ApplicationErrorMessage.USER_NOT_FOUND,
                        refreshToken.email()
                ));

        if (!user.enabled()){
            throw new ApplicationException(ApplicationErrorMessage.ACCOUNT_DISABLED, user.email());
        }

        refreshTokenRepositoryPort.revokeByToken(refreshToken.token());

        String newAccessToken = tokenPort.generateAccessToken(user);

        RefreshToken newRefreshToken = new  RefreshToken(
                user.email(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );

        refreshTokenRepositoryPort.save(newRefreshToken);

        return new TokenResponse(
                newAccessToken,
                newRefreshToken.token(),
                "Bearer",
                900000L, // 15 minutes in milliseconds
                LocalDateTime.now()
        );
    }
}
