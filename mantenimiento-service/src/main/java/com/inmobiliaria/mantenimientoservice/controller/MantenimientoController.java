package com.inmobiliaria.mantenimientoservice.controller;

import com.inmobiliaria.mantenimientoservice.model.Mantenimiento;
import com.inmobiliaria.mantenimientoservice.repository.MantenimientoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mantenimientos")
public class MantenimientoController {

    @Autowired private MantenimientoRepository repository;

    @GetMapping
    public List<Mantenimiento> listar() {
        log.info("GET /api/mantenimientos - Listando solicitudes");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mantenimiento> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/mantenimientos/{}", id);
        return repository.findById(id)
                .map(m -> { log.info("Mantenimiento encontrado ID: {}", id); return ResponseEntity.ok(m); })
                .orElseGet(() -> { log.error("Mantenimiento ID {} no encontrado", id); return ResponseEntity.notFound().build(); });
    }

    @GetMapping("/propiedad/{idPropiedad}")
    public List<Mantenimiento> porPropiedad(@PathVariable Long idPropiedad) {
        log.info("GET /api/mantenimientos/propiedad/{}", idPropiedad);
        return repository.findByIdPropiedad(idPropiedad);
    }

    @PostMapping
    public Mantenimiento crear(@RequestBody Mantenimiento m) {
        log.info("POST /api/mantenimientos - Propiedad: {}, Prioridad: {}", m.getIdPropiedad(), m.getPrioridad());
        m.setEstado(Mantenimiento.Estado.PENDIENTE);
        m.setFechaSolicitud(LocalDate.now());
        Mantenimiento saved = repository.save(m);
        log.info("Mantenimiento creado ID: {}", saved.getId());
        return saved;
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<Mantenimiento> resolver(@PathVariable Long id) {
        log.info("PUT /api/mantenimientos/{}/resolver", id);
        return repository.findById(id).map(m -> {
            m.setEstado(Mantenimiento.Estado.RESUELTO);
            m.setFechaResolucion(LocalDate.now());
            log.info("Mantenimiento {} resuelto", id);
            return ResponseEntity.ok(repository.save(m));
        }).orElseGet(() -> { log.error("Mantenimiento ID {} no encontrado", id); return ResponseEntity.notFound().build(); });
    }
}
