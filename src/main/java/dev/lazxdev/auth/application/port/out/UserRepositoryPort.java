package dev.lazxdev.auth.application.port.out;

import dev.lazxdev.auth.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
