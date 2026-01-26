package dev.lazxdev.auth.application.service;

import dev.lazxdev.auth.application.dto.AuthRequest;
import dev.lazxdev.auth.application.dto.RegisterResponse;
import dev.lazxdev.auth.application.port.in.RegisterUserUseCase;
import dev.lazxdev.auth.application.port.out.PasswordEncoderPort;
import dev.lazxdev.auth.application.port.out.UserRepositoryPort;
import dev.lazxdev.auth.domain.model.RoleType;
import dev.lazxdev.auth.domain.model.User;
import dev.lazxdev.auth.infrastructure.exceptions.ApplicationErrorMessage;
import dev.lazxdev.auth.infrastructure.exceptions.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Transactional
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;

    public RegisterUserService(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse register(AuthRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ApplicationException(
                    ApplicationErrorMessage.EMAIL_ALREADY_REGISTERED,
                    request.email()
            );
        }

        LocalDateTime now = LocalDateTime.now();
        User user = new User(
                request.email(),
                encodePassword(request.password()),
                RoleType.USER,
                true,
                now,
                now
        );

        User savedUser = userRepository.save(user);

        return mapToUserResponse(savedUser);
    }

    private String encodePassword(String rawPassword) {
        try {
            return passwordEncoder.encode(rawPassword);
        } catch (Exception e) {
            throw new ApplicationException(
                    ApplicationErrorMessage.PASSWORD_HASHING_ERROR,
                    e
            );
        }
    }

    private RegisterResponse mapToUserResponse(User user) {
        Set<String> roleNames = Set.of(user.role().name());

        return new RegisterResponse(
                user.email(),
                roleNames,
                user.enabled(),
                user.createdAt()
        );
    }
}