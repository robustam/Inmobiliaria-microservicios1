package com.inmobiliaria.propiedadservice.controller;

import com.inmobiliaria.propiedadservice.model.Propiedad;
import com.inmobiliaria.propiedadservice.repository.PropiedadRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    @Autowired private PropiedadRepository repository;

    @GetMapping
    public List<Propiedad> listarTodas() {
        log.info("GET /api/propiedades - Listando todas las propiedades");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Propiedad> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/propiedades/{} - Buscando propiedad", id);
        return repository.findById(id)
                .map(p -> { log.info("Propiedad encontrada: {}", p.getDireccion()); return ResponseEntity.ok(p); })
                .orElseGet(() -> { log.error("Propiedad con ID {} no encontrada", id); return ResponseEntity.notFound().build(); });
    }

    @PostMapping
    public ResponseEntity<Propiedad> guardar(@Valid @RequestBody Propiedad propiedad) {
        log.info("POST /api/propiedades - Creando propiedad: {}", propiedad.getDireccion());
        Propiedad saved = repository.save(propiedad);
        log.info("Propiedad creada con ID: {}", saved.getId());
        return ResponseEntity.ok(saved);
    }
}
