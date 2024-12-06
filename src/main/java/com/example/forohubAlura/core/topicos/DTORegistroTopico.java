package com.example.forohubAlura.core.topicos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DTORegistroTopico(

        @NotNull(message = "El título no puede ser nulo.")
        @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres.")
        String titulo,

        @NotNull(message = "El mensaje no puede ser nulo.")
        @Size(min = 10, max = 5000, message = "El mensaje debe tener entre 10 y 5000 caracteres.")
        String mensaje,

        @NotNull(message = "El autor no puede ser nulo.")
        @Size(min = 3, max = 100, message = "El nombre del autor debe tener entre 3 y 100 caracteres.")
        String autor,

        @NotNull(message = "El curso no puede ser nulo.")
        @Size(min = 3, max = 100, message = "El nombre del curso debe tener entre 3 y 100 caracteres.")
        String curso

) {
}
