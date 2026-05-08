package com.inmobiliaria.contratoservice.controller;

import com.inmobiliaria.contratoservice.model.Contrato;
import com.inmobiliaria.contratoservice.repository.ContratoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    @Autowired private ContratoRepository repository;

    @GetMapping
    public List<Contrato> listar() {
        log.info("GET /api/contratos - Listando contratos");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/contratos/{} - Buscando contrato", id);
        return repository.findById(id)
                .map(c -> { log.info("Contrato encontrado ID: {}", id); return ResponseEntity.ok(c); })
                .orElseGet(() -> { log.error("Contrato ID {} no encontrado", id); return ResponseEntity.notFound().build(); });
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Contrato> porUsuario(@PathVariable Long idUsuario) {
        log.info("GET /api/contratos/usuario/{}", idUsuario);
        return repository.findByIdUsuario(idUsuario);
    }

    @PostMapping
    public Contrato crear(@RequestBody Contrato contrato) {
        log.info("POST /api/contratos - Usuario: {}, Propiedad: {}", contrato.getIdUsuario(), contrato.getIdPropiedad());
        contrato.setEstado(Contrato.Estado.ACTIVO);
        Contrato saved = repository.save(contrato);
        log.info("Contrato creado ID: {}", saved.getId());
        return saved;
    }

    @PutMapping("/{id}/terminar")
    public ResponseEntity<Contrato> terminar(@PathVariable Long id) {
        log.info("PUT /api/contratos/{}/terminar", id);
        return repository.findById(id).map(c -> {
            c.setEstado(Contrato.Estado.TERMINADO);
            log.info("Contrato {} terminado", id);
            return ResponseEntity.ok(repository.save(c));
        }).orElseGet(() -> { log.error("Contrato ID {} no encontrado", id); return ResponseEntity.notFound().build(); });
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Contrato> cancelar(@PathVariable Long id) {
        log.info("PUT /api/contratos/{}/cancelar", id);
        return repository.findById(id).map(c -> {
            c.setEstado(Contrato.Estado.CANCELADO);
            log.info("Contrato {} cancelado", id);
            return ResponseEntity.ok(repository.save(c));
        }).orElseGet(() -> { log.error("Contrato ID {} no encontrado", id); return ResponseEntity.notFound().build(); });
    }
}
