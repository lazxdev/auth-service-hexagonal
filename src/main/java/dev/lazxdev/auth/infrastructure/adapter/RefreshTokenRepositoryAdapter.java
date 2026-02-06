package dev.lazxdev.auth.infrastructure.adapter;

import dev.lazxdev.auth.application.port.out.RefreshTokenRepositoryPort;
import dev.lazxdev.auth.domain.model.RefreshToken;
import dev.lazxdev.auth.infrastructure.mappers.RefreshTokenMapper;
import dev.lazxdev.auth.infrastructure.persistence.repository.JpaRefreshTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {

    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    public RefreshTokenRepositoryAdapter(JpaRefreshTokenRepository jpaRefreshTokenRepository, RefreshTokenMapper refreshTokenMapper) {
        this.jpaRefreshTokenRepository = jpaRefreshTokenRepository;
        this.refreshTokenMapper = refreshTokenMapper;
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenMapper.toDomain(
                jpaRefreshTokenRepository.save(refreshTokenMapper.toEntity(refreshToken))
        );
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return jpaRefreshTokenRepository.findByToken(token)
                .map(refreshTokenMapper::toDomain);
    }

    @Override
    public void revokeByEmail(String email) {
        jpaRefreshTokenRepository.revokeByEmail(email);
    }

    @Override
    public void revokeByToken(String token) {
        jpaRefreshTokenRepository.revokeByToken(token);
    }
}
