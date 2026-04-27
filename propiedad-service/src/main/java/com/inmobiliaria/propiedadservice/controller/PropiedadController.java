package com.inmobiliaria.propiedadservice.controller;

import com.inmobiliaria.propiedadservice.model.Propiedad;
import com.inmobiliaria.propiedadservice.repository.PropiedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    @Autowired
    private PropiedadRepository repository;

    @GetMapping
    public List<Propiedad> listarTodas() {
        return repository.findAll();
    }

    // ✅ ENDPOINT NUEVO — requerido por reservas-service via Feign
    @GetMapping("/{id}")
    public ResponseEntity<Propiedad> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Propiedad guardar(@RequestBody Propiedad propiedad) {
        return repository.save(propiedad);
    }
}