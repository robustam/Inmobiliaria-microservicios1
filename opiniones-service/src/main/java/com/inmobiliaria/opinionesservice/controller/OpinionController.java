package com.inmobiliaria.opinionesservice.controller;

import com.inmobiliaria.opinionesservice.model.Opinion;
import com.inmobiliaria.opinionesservice.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/opiniones")
public class OpinionController {

    @Autowired
    private OpinionService opinionService;

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody Opinion opinion) {
        try {
            Opinion nuevaOpinion = opinionService.guardarOpinion(opinion);
            return ResponseEntity.ok(nuevaOpinion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}