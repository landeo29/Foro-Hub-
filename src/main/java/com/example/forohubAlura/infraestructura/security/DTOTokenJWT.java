package com.example.forohubAlura.infraestructura.security;

public record DTOTokenJWT(
        String jwtToken
) {


    public DTOTokenJWT {
        if (jwtToken == null || jwtToken.trim().isEmpty()) {
            throw new IllegalArgumentException("El token JWT no puede ser nulo o vacío");
        }
    }
}
