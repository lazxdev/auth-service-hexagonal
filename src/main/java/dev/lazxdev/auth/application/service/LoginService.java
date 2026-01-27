package dev.lazxdev.auth.application.service;

import dev.lazxdev.auth.application.dto.AuthRequest;
import dev.lazxdev.auth.application.dto.TokenResponse;
import dev.lazxdev.auth.application.port.in.LoginUseCase;
import dev.lazxdev.auth.application.port.out.PasswordEncoderPort;
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
public class LoginService implements LoginUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenPort tokenPort;

    public LoginService(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            TokenPort tokenPort) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenPort = tokenPort;
    }

    @Override
    public TokenResponse login(AuthRequest request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(()-> new ApplicationException(
                        ApplicationErrorMessage.INVALID_CREDENTIALS,
                        "Invalid email or password"
                ));

        if (!user.enabled()){
            throw new ApplicationException(
                    ApplicationErrorMessage.ACCOUNT_DISABLED,
                    user.email()
            );
        }

        if (!passwordEncoder.matches(request.password(), user.password())){
            throw new ApplicationException(
                    ApplicationErrorMessage.INVALID_CREDENTIALS,
                    "Invalid email or password"
            );
        }

        String accessToken = tokenPort.generateAccessToken(user);
        String refreshToken = tokenPort.generateRefreshToken(user);

        return new TokenResponse(
                accessToken,
                refreshToken,
                "Bearer",
                900000L, //15 minutes in milliseconds
                LocalDateTime.now()
                );
    }

}
