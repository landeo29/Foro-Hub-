package com.example.forohubAlura.core.topicos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record DTORespuestaRegistroTopico(
        Long id,
        String titulo,
        String mensaje,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime fechaCreacion,
        String autor,
        String curso
) {
    public DTORespuestaRegistroTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getAutor(),
                topico.getCurso()
        );
    }
}
