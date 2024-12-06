package com.example.forohubAlura.infraestructura.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.forohubAlura.core.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(SecurityFilter.class.getName());

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            try {
                String subject = tokenService.getSubject(token);

                if (subject != null) {
                    var usuarioOptional = usuarioRepository.findByUser(subject);

                    if (usuarioOptional.isPresent()) {
                        UserDetails usuario = usuarioOptional.get();

                        var authentication = new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                usuario.getAuthorities()
                        );

                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        logger.info("Autenticación exitosa para el usuario: " + subject);
                    } else {
                        logger.warning("Usuario no encontrado: " + subject);
                    }
                }
            } catch (Exception e) {
                logger.severe("Error al procesar el token: " + e.getMessage());
            }
        } else {
            logger.warning("No se proporcionó un token de autorización válido");
        }

        filterChain.doFilter(request, response);
    }
}
