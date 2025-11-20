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
public class TokenService{

    @Value("${api.security.token.secret}")
    private String secret;

    public String generatedToken(Usuario usuario){

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-ROOMIE")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(getExpiracionDate())
                    .sign(algorithm);
            return token;

        }catch (JWTCreationException e){
            throw new RuntimeException("Problema na criação do token" + e);
        }
    }

    public String validateToken(String token){

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-ROOMIE")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException e){
            return "";
        }
    }

    private Instant getExpiracionDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}