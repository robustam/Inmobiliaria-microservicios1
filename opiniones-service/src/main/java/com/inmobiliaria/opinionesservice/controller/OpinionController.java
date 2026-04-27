package com.inmobiliaria.opinionesservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.inmobiliaria.opinionesservice.model.Opinion;
import com.inmobiliaria.opinionesservice.service.OpinionService;
import java.util.List;

@RestController
@RequestMapping("/api/opiniones")
public class OpinionController {

    @Autowired
    private OpinionService opinionService;

    @PostMapping
    public ResponseEntity<Opinion> crearOpinion(@RequestBody Opinion opinion) {
        Opinion opinionCreada = opinionService.crearOpinion(opinion);
        return ResponseEntity.status(HttpStatus.CREATED).body(opinionCreada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Opinion> obtenerOpinion(@PathVariable Long id) {
        Opinion opinion = opinionService.obtenerOpinion(id);
        return ResponseEntity.ok(opinion);
    }

    @GetMapping
    public ResponseEntity<List<Opinion>> obtenerTodasLasOpiniones() {
        List<Opinion> opiniones = opinionService.obtenerTodasLasOpiniones();
        return ResponseEntity.ok(opiniones);
    }

    @GetMapping("/propiedad/{idPropiedad}")
    public ResponseEntity<List<Opinion>> obtenerOpinionesPorPropiedad(@PathVariable Long idPropiedad) {
        List<Opinion> opiniones = opinionService.obtenerOpinionesPorPropiedad(idPropiedad);
        return ResponseEntity.ok(opiniones);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Opinion>> obtenerOpinionesPorUsuario(@PathVariable Long idUsuario) {
        List<Opinion> opiniones = opinionService.obtenerOpinionesPorUsuario(idUsuario);
        return ResponseEntity.ok(opiniones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Opinion> actualizarOpinion(@PathVariable Long id, @RequestBody Opinion opinionActualizada) {
        Opinion opinion = opinionService.actualizarOpinion(id, opinionActualizada);
        return ResponseEntity.ok(opinion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOpinion(@PathVariable Long id) {
        opinionService.eliminarOpinion(id);
        return ResponseEntity.noContent().build();
    }
}