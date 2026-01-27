package dev.lazxdev.auth.infrastructure.adapter;

import dev.lazxdev.auth.application.port.out.TokenPort;
import dev.lazxdev.auth.domain.model.User;
import dev.lazxdev.auth.infrastructure.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenAdapter implements TokenPort {

    private final JwtConfig jwtConfig;
    private final SecretKey accessTokenKey;
    private final SecretKey refreshTokenKey;

    public JwtTokenAdapter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.accessTokenKey = Keys.hmacShaKeyFor(
                jwtConfig.getAccessToken().getSecret().getBytes(StandardCharsets.UTF_8)
        );
        this.refreshTokenKey = Keys.hmacShaKeyFor(
                jwtConfig.getRefreshToken().getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(jwtConfig.getAccessToken().getExpiration());

        return Jwts.builder()
                .subject(user.email())
                .claim("userId", user.id().toString())
                .claim("email", user.email())
                .claim("role", user.role().name())
                .claim("type", "ACCESS")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(accessTokenKey, Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(jwtConfig.getRefreshToken().getExpiration());
        String tokenId = UUID.randomUUID().toString();

        return Jwts.builder()
                .subject(user.email())
                .claim("userId", user.id().toString())
                .claim("email", user.email())
                .claim("tokenId", tokenId)
                .claim("type", "REFRESH")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(refreshTokenKey, Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public boolean validateAccessToken(String token) {
        try {
            parseToken(token, accessTokenKey, "ACCESS");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean validateRefreshToken(String token) {
        try {
            parseToken(token, refreshTokenKey, "REFRESH");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String extractEmailFromToken(String token) {
        try {
            // Intentar con access token primero
            Claims claims = parseToken(token, accessTokenKey, null);
            return claims.get("email", String.class);
        } catch (JwtException e) {
            // Si falla, intentar con refresh token
            try {
                Claims claims = parseToken(token, refreshTokenKey, null);
                return claims.get("email", String.class);
            } catch (JwtException ex) {
                throw new JwtException("Invalid token: " + ex.getMessage());
            }
        }
    }

    @Override
    public String extractTokenIdFromToken(String token) {
        try {
            Claims claims = parseToken(token, refreshTokenKey, "REFRESH");
            return claims.get("tokenId", String.class);
        } catch (Exception e) {
            throw new JwtException("Invalid refresh token: " + e.getMessage());
        }
    }

    private Claims parseToken(String token, SecretKey key, String expectedType) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Validar tipo de token si se especifica
        if (expectedType != null) {
            String tokenType = claims.get("type", String.class);
            if (!expectedType.equals(tokenType)) {
                throw new JwtException("Invalid token type. Expected: " + expectedType);
            }
        }

        // Validar expiración
        if (claims.getExpiration().before(new Date())) {
            throw new JwtException("Token has expired");
        }

        return claims;
    }
}
