package com.project.roomie.infra.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.roomie.core.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class RefreshTokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateRefreshToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-ROOMIE-REFRESH")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(getRefreshExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Problema na criação do refresh token: " + e);
        }
    }

    public String validateRefreshToken(String refreshToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-ROOMIE-REFRESH")
                    .build()
                    .verify(refreshToken)
                    .getSubject();

        } catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant getRefreshExpirationDate() {
        return LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
    }
}