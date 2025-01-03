package com.example.forohubAlura.core.topicos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record DTORespuestaListadoTopicos(
        Long id,
        String titulo,
        String mensaje,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime fechaCreacion,
        Boolean activo,
        String autor,
        String curso
) {
    public DTORespuestaListadoTopicos(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getActivo(),
                topico.getAutor(),
                topico.getCurso()
        );
    }
}
