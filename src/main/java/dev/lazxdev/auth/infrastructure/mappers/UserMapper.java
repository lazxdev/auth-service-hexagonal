package dev.lazxdev.auth.infrastructure.mappers;

import dev.lazxdev.auth.domain.model.User;
import dev.lazxdev.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {


    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.isEnabled(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        return new UserEntity(
                domain.id(),
                domain.email(),
                domain.password(),
                domain.role(),
                domain.enabled(),
                domain.createdAt(),
                domain.updatedAt()
        );
    }
}