package com.example.forohubAlura.core.topicos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    private Boolean activo;
    private String autor;
    private String curso;

    //metodos

    public Topico(DTORegistroTopico dtoRegistroTopico){
        this.titulo = dtoRegistroTopico.titulo();
        this.mensaje = dtoRegistroTopico.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
        this.autor = dtoRegistroTopico.autor();
        this.curso = dtoRegistroTopico.curso();
    }


}
