package dev.lazxdev.auth.infrastructure.mappers;

import dev.lazxdev.auth.domain.model.RefreshToken;
import dev.lazxdev.auth.infrastructure.persistence.entity.RefreshTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

    public RefreshToken toDomain(RefreshTokenEntity entity){
        if (entity == null){
            return null;
        }

        return new RefreshToken(
                entity.getId(),
                entity.getToken(),
                entity.getEmail(),
                entity.getIssuedAt(),
                entity.getExpiresAt(),
                entity.isRevoked()
        );
    }

    public RefreshTokenEntity toEntity(RefreshToken domain) {
        if (domain == null) {
            return null;
        }

        return new RefreshTokenEntity(
                domain.id(),
                domain.token(),
                domain.email(),
                domain.issuedAt(),
                domain.expiresAt(),
                domain.revoked()
        );
    }

}
