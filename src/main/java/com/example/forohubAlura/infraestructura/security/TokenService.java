package com.example.forohubAlura.infraestructura.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.forohubAlura.core.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${JWT_SECRET}")
    private String secret;

    @Value("${JWT_EXPIRATION_TIME_HOURS:2}")
    private int expirationTimeInHours;

    public String generarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("CH3_BACKEND")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(fechaExpiracion())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    private Instant fechaExpiracion() {
        return LocalDateTime.now()
                .plusHours(expirationTimeInHours)
                .toInstant(ZoneOffset.UTC);
    }

    // Método para verificar la validez del token
    public String getSubject(String token) {
        try {
            DecodedJWT verifier = verificarToken(token);

            if (verifier.getSubject() != null) {
                return verifier.getSubject();
            } else {
                throw new RuntimeException("El token no tiene un 'subject' válido");
            }
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Error al verificar el token", exception);
        }
    }

    private DecodedJWT verificarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("CH3_BACKEND")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido o expirado", exception);
        }
    }
}
