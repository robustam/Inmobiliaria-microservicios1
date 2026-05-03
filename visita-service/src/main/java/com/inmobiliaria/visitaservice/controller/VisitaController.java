package com.inmobiliaria.visitaservice.controller;

import com.inmobiliaria.visitaservice.model.Visita;
import com.inmobiliaria.visitaservice.repository.VisitaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/visitas")
public class VisitaController {

    @Autowired private VisitaRepository repository;

    @GetMapping
    public List<Visita> listar() {
        log.info("GET /api/visitas - Listando visitas");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visita> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/visitas/{}", id);
        return repository.findById(id)
                .map(v -> { log.info("Visita encontrada ID: {}", id); return ResponseEntity.ok(v); })
                .orElseGet(() -> { log.error("Visita ID {} no encontrada", id); return ResponseEntity.notFound().build(); });
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Visita> porUsuario(@PathVariable Long idUsuario) {
        log.info("GET /api/visitas/usuario/{}", idUsuario);
        return repository.findByIdUsuario(idUsuario);
    }

    @PostMapping
    public Visita agendar(@RequestBody Visita visita) {
        log.info("POST /api/visitas - Usuario: {}, Propiedad: {}", visita.getIdUsuario(), visita.getIdPropiedad());
        visita.setEstado(Visita.Estado.PENDIENTE);
        Visita saved = repository.save(visita);
        log.info("Visita agendada ID: {}", saved.getId());
        return saved;
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Visita> confirmar(@PathVariable Long id) {
        log.info("PUT /api/visitas/{}/confirmar", id);
        return repository.findById(id).map(v -> {
            v.setEstado(Visita.Estado.CONFIRMADA);
            log.info("Visita {} confirmada", id);
            return ResponseEntity.ok(repository.save(v));
        }).orElseGet(() -> { log.error("Visita ID {} no encontrada", id); return ResponseEntity.notFound().build(); });
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Visita> cancelar(@PathVariable Long id) {
        log.info("PUT /api/visitas/{}/cancelar", id);
        return repository.findById(id).map(v -> {
            v.setEstado(Visita.Estado.CANCELADA);
            log.info("Visita {} cancelada", id);
            return ResponseEntity.ok(repository.save(v));
        }).orElseGet(() -> { log.error("Visita ID {} no encontrada", id); return ResponseEntity.notFound().build(); });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/visitas/{}", id);
        if (!repository.existsById(id)) {
            log.error("Visita ID {} no encontrada para eliminar", id);
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        log.info("Visita {} eliminada", id);
        return ResponseEntity.noContent().build();
    }
}
