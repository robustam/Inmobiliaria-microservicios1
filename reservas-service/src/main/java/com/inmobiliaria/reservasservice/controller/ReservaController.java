package com.inmobiliaria.reservasservice.controller;

import com.inmobiliaria.reservasservice.model.Reserva;
import com.inmobiliaria.reservasservice.service.IReservaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private IReservaService reservaServ;

    @PostMapping("/crear")
    public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva) {
        log.info("POST /api/reservas/crear - Usuario: {}, Propiedad: {}", reserva.getIdUsuario(), reserva.getIdPropiedad());
        try {
            Reserva saved = reservaServ.saveReserva(reserva);
            log.info("Reserva creada ID: {}", saved.getId());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            log.error("Error al crear reserva: {}", e.getMessage());
            return ResponseEntity.status(500).body(
                    java.util.Map.of("error", e.getMessage(), "status", 500)
            );
        }
    }

    @GetMapping("/listar")
    public List<Reserva> listarReservas() {
        log.info("GET /api/reservas/listar");
        return reservaServ.getReservas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReserva(@PathVariable Long id) {
        log.info("GET /api/reservas/{}", id);
        Reserva reserva = reservaServ.findReserva(id);
        if (reserva == null) {
            log.error("Reserva ID {} no encontrada", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reserva);
    }
}
