package com.example.forohubAlura.controller;

import com.example.forohubAlura.core.topicos.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DTORespuestaRegistroTopico> registroTopico(
            @RequestBody @Valid DTORegistroTopico dtoRegistroTopico,
            UriComponentsBuilder uriComponentsBuilder) {

        var topico = topicoRepository.save(new Topico(dtoRegistroTopico));

        var response = new DTORespuestaRegistroTopico(topico);

        URI url = uriComponentsBuilder
                .path("/topicos/{id}")
                .buildAndExpand(topico.getId()).toUri();

        return ResponseEntity
                .created(url)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<Page<DTORespuestaListadoTopicos>> getAllTopicos(
            @PageableDefault(size = 10) Pageable pageable) {

        Page<DTORespuestaListadoTopicos> topicos = topicoRepository.findAll(pageable)
                .map(DTORespuestaListadoTopicos::new);

        return ResponseEntity.ok(topicos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DTORespuestaTopicoPorId> getTopicoById(@PathVariable Long id) {

        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            return ResponseEntity.ok(new DTORespuestaTopicoPorId(topicoOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DTORespuestaActualizarTopico> updateTopico(
            @PathVariable Long id,
            @RequestBody @Valid DTOActualizarTopico dtoActualizarTopico) {

        // Verificar si el tópico existe
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            topico.setTitulo(dtoActualizarTopico.titulo());
            topico.setMensaje(dtoActualizarTopico.mensaje());
            topico.setAutor(dtoActualizarTopico.autor());
            topico.setCurso(dtoActualizarTopico.curso());

            topicoRepository.save(topico);

            return ResponseEntity.ok(new DTORespuestaActualizarTopico(topico));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTopico(@PathVariable Long id) {

        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
