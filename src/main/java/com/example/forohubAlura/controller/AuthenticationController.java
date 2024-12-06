package com.example.forohubAlura.controller;

import jakarta.validation.Valid;
import com.example.forohubAlura.core.usuario.DTOUsuarioLogin;
import com.example.forohubAlura.core.usuario.Usuario;
import com.example.forohubAlura.infraestructura.security.DTOTokenJWT;
import com.example.forohubAlura.infraestructura.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid DTOUsuarioLogin dtoUsuarioLogin) {

        try {
            var authentication = new UsernamePasswordAuthenticationToken(
                    dtoUsuarioLogin.username(),
                    dtoUsuarioLogin.password()
            );

            var autorizado = authenticationManager.authenticate(authentication);

            var jwtToken = tokenService.generarToken((Usuario) autorizado.getPrincipal());

            return ResponseEntity.ok(new DTOTokenJWT(jwtToken));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }
    }
}
