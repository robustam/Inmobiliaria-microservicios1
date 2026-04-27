package com.inmobiliaria.reservasservice.controller;

import com.inmobiliaria.reservasservice.model.Reserva;
import com.inmobiliaria.reservasservice.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private IReservaService reservaServ;

    @PostMapping("/crear")
    public String crearReserva(@RequestBody Reserva reserva) {
        return reservaServ.saveReserva(reserva);
    }

    @GetMapping("/listar")
    public List<Reserva> listarReservas() {
        return reservaServ.getReservas();
    }

    @GetMapping("/{id}")
    public Reserva buscarReservaPorId(@PathVariable Long id) {
        return reservaServ.getReservas().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // En ReservaController de reservas-service
    @GetMapping("/{id}")
    public Reserva getReserva(@PathVariable Long id) {
        // Lógica para retornar la reserva por ID
        return reservaServ.findReserva(id);
    }

}