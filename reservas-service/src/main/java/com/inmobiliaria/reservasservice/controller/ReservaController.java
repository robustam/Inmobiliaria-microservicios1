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

    @Autowired private IReservaService reservaServ;

    @PostMapping("/crear")
    public String crearReserva(@RequestBody Reserva reserva) {
        log.info("POST /api/reservas/crear - Usuario: {}, Propiedad: {}", reserva.getIdUsuario(), reserva.getIdPropiedad());
        String resultado = reservaServ.saveReserva(reserva);
        log.info("Resultado: {}", resultado);
        return resultado;
    }

    @GetMapping("/listar")
    public List<Reserva> listarReservas() {
        log.info("GET /api/reservas/listar - Listando reservas");
        List<Reserva> reservas = reservaServ.getReservas();
        log.debug("Total reservas: {}", reservas.size());
        return reservas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReserva(@PathVariable Long id) {
        log.info("GET /api/reservas/{} - Buscando reserva", id);
        Reserva reserva = reservaServ.findReserva(id);
        if (reserva == null) {
            log.error("Reserva con ID {} no encontrada", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Reserva encontrada: ID {}", reserva.getId());
        return ResponseEntity.ok(reserva);
    }
}
