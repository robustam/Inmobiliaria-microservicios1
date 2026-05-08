package com.inmobiliaria.documentoservice.controller;

import com.inmobiliaria.documentoservice.model.Documento;
import com.inmobiliaria.documentoservice.repository.DocumentoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired private DocumentoRepository repository;

    @GetMapping
    public List<Documento> listar() {
        log.info("GET /api/documentos - Listando documentos");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/documentos/{}", id);
        return repository.findById(id)
                .map(d -> { log.info("Documento encontrado: {}", d.getNombre()); return ResponseEntity.ok(d); })
                .orElseGet(() -> { log.error("Documento ID {} no encontrado", id); return ResponseEntity.notFound().build(); });
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Documento> porUsuario(@PathVariable Long idUsuario) {
        log.info("GET /api/documentos/usuario/{}", idUsuario);
        return repository.findByIdUsuario(idUsuario);
    }

    @GetMapping("/contrato/{idContrato}")
    public List<Documento> porContrato(@PathVariable Long idContrato) {
        log.info("GET /api/documentos/contrato/{}", idContrato);
        return repository.findByIdContrato(idContrato);
    }

    @PostMapping
    public Documento subir(@RequestBody Documento doc) {
        log.info("POST /api/documentos - Tipo: {}, Nombre: {}", doc.getTipo(), doc.getNombre());
        doc.setFechaSubida(LocalDate.now());
        doc.setFirmado(false);
        Documento saved = repository.save(doc);
        log.info("Documento subido con ID: {}", saved.getId());
        return saved;
    }

    @PutMapping("/{id}/firmar")
    public ResponseEntity<Documento> firmar(@PathVariable Long id) {
        log.info("PUT /api/documentos/{}/firmar", id);
        return repository.findById(id).map(doc -> {
            doc.setFirmado(true);
            log.info("Documento {} firmado exitosamente", id);
            return ResponseEntity.ok(repository.save(doc));
        }).orElseGet(() -> { log.error("Documento ID {} no encontrado para firmar", id); return ResponseEntity.notFound().build(); });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/documentos/{}", id);
        if (!repository.existsById(id)) {
            log.error("Documento ID {} no encontrado para eliminar", id);
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        log.info("Documento {} eliminado", id);
        return ResponseEntity.noContent().build();
    }
}
