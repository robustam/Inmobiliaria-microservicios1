package com.inmobiliaria.reservasservice.service;

import com.inmobiliaria.reservasservice.model.Reserva;
import com.inmobiliaria.reservasservice.repository.IReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService implements IReservaService {

    @Autowired
    private IReservaRepository reservaRepo;

    // Aquí inyectaremos los Clientes Feign (UsuarioClient y PropiedadClient) en el siguiente paso

    @Override
    public String saveReserva(Reserva reserva) {
        // Lógica futura:
        // 1. Validar Usuario via Feign
        // 2. Validar Propiedad via Feign

        reserva.setEstado("CONFIRMADA");
        reservaRepo.save(reserva);
        return "Reserva creada con éxito para la propiedad ID: " + reserva.getIdPropiedad();
    }

    @Override
    public List<Reserva> getReservas() {
        return reservaRepo.findAll();
    }
}