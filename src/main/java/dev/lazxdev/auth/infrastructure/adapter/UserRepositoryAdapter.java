package dev.lazxdev.auth.infrastructure.adapter;

import dev.lazxdev.auth.application.port.out.UserRepositoryPort;
import dev.lazxdev.auth.domain.model.User;
import dev.lazxdev.auth.infrastructure.mappers.UserMapper;
import dev.lazxdev.auth.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        return userMapper.toDomain(
                jpaUserRepository.save(userMapper.toEntity(user))
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}