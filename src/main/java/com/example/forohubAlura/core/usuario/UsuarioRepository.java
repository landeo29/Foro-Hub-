package com.example.forohubAlura.core.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     * Usamos Optional para manejar correctamente la ausencia de un resultado.
     *
     * @param username nombre de usuario
     * @return un Optional con el Usuario encontrado, o vacío si no existe.
     */
    Optional<Usuario> findByUser(String username);

    /**
     * Busca un usuario por su id, si es necesario para otras operaciones.
     *
     * @param id identificador del usuario
     * @return un Optional con el Usuario encontrado, o vacío si no existe.
     */
    Optional<Usuario> findById(Long id);

}
