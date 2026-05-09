package com.inmobiliaria.reservasservice.service;

import com.inmobiliaria.reservasservice.dto.PropiedadDTO;
import com.inmobiliaria.reservasservice.dto.UsuarioDTO;
import com.inmobiliaria.reservasservice.model.Reserva;
import com.inmobiliaria.reservasservice.repository.IReservaRepository;
import com.inmobiliaria.reservasservice.repository.client.IPropiedadClient;
import com.inmobiliaria.reservasservice.repository.client.IUsuarioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class ReservaService implements IReservaService {

    @Autowired
    private IReservaRepository reservaRepo;

    @Autowired
    private IUsuarioClient usuarioClient;

    @Autowired
    private IPropiedadClient propiedadClient;

    @Override
    public Reserva saveReserva(Reserva reserva) {
        log.info("Validando usuario ID: {}", reserva.getIdUsuario());
        UsuarioDTO user = usuarioClient.buscarUsuario(reserva.getIdUsuario());

        log.info("Validando propiedad ID: {}", reserva.getIdPropiedad());
        PropiedadDTO prop = propiedadClient.buscarPropiedad(reserva.getIdPropiedad());

        if (user == null) throw new RuntimeException("Error: El usuario no existe.");
        if (prop == null) throw new RuntimeException("Error: La propiedad no existe.");

        reserva.setEstado("CONFIRMADA");
        Reserva saved = reservaRepo.save(reserva);
        log.info("Reserva creada ID: {} para usuario: {}", saved.getId(), user.getNombre());
        return saved;
    }

    @Override
    public List<Reserva> getReservas() {
        return reservaRepo.findAll();
    }

    @Override
    public Reserva findReserva(Long id) {
        return reservaRepo.findById(id).orElse(null);
    }
}
