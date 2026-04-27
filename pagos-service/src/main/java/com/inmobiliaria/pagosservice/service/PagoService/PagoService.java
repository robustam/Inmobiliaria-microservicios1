package com.inmobiliaria.pagosservice.service.PagoService;

import com.inmobiliaria.pagosservice.dto.ReservaDTO;
import com.inmobiliaria.pagosservice.model.Pago;
import com.inmobiliaria.pagosservice.repository.IPagoRepository;
import com.inmobiliaria.pagosservice.repository.client.IReservaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    @Autowired
    private IPagoRepository pagoRepo;

    @Autowired
    private IReservaClient reservaClient;

    public String procesarPago(Pago pago) {
        // 1. Validamos que la reserva exista llamando al otro microservicio
        ReservaDTO reserva = reservaClient.buscarReserva(pago.getIdReserva());

        if (reserva == null) {
            return "Error: La reserva no existe.";
        }

        // 2. Simulación de lógica de negocio
        pago.setEstado("REALIZADO");
        pagoRepo.save(pago);

        return "Pago de $" + pago.getMonto() + " procesado con éxito para la reserva #" + reserva.getId();
    }

    public List<Pago> listarTodos() {
        return pagoRepo.findAll();
    }
}