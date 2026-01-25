package dev.lazxdev.auth.application.service;

import dev.lazxdev.auth.application.dto.RegisterRequest;
import dev.lazxdev.auth.application.dto.UserResponse;
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
import java.util.stream.Collectors;

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
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ApplicationException(
                    ApplicationErrorMessage.EMAIL_ALREADY_REGISTERED,
                    request.email()
            );
        }

        // Crear el usuario usando el constructor del record
        LocalDateTime now = LocalDateTime.now();
        User user = new User(
                null, // El ID se generará en la base de datos
                request.email(),
                encodePassword(request.password()),
                RoleType.USER,
                true,
                now,
                now
        );

        // Guardar el usuario
        User savedUser = userRepository.save(user);

        // Retornar respuesta
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

    private UserResponse mapToUserResponse(User user) {
        Set<String> roleNames = Set.of(user.role().name());

        return new UserResponse(
                user.id(),
                user.email(),
                roleNames,
                user.enabled(),
                user.createdAt()
        );
    }
}