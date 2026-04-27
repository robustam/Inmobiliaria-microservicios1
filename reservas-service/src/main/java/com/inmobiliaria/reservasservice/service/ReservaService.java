package com.inmobiliaria.reservasservice.service;

import com.inmobiliaria.reservasservice.dto.PropiedadDTO;
import com.inmobiliaria.reservasservice.dto.UsuarioDTO;
import com.inmobiliaria.reservasservice.model.Reserva;
import com.inmobiliaria.reservasservice.repository.IReservaRepository;
import com.inmobiliaria.reservasservice.repository.client.IPropiedadClient;
import com.inmobiliaria.reservasservice.repository.client.IUsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService implements IReservaService {

    @Autowired
    private IReservaRepository reservaRepo;

    @Autowired
    private IUsuarioClient usuarioClient; // El puente a Usuarios

    @Autowired
    private IPropiedadClient propiedadClient; // El puente a Propiedades

    @Override
    public String saveReserva(Reserva reserva) {

        // 1. Validamos si el Usuario existe llamando al otro microservicio
        UsuarioDTO user = usuarioClient.buscarUsuario(reserva.getIdUsuario());

        // 2. Validamos si la Propiedad existe llamando al otro microservicio
        PropiedadDTO prop = propiedadClient.buscarPropiedad(reserva.getIdPropiedad());

        // 3. Lógica de seguridad
        if (user == null) return "Error: El usuario no existe.";
        if (prop == null) return "Error: La propiedad no existe.";

        // 4. Si todo está bien, guardamos
        reserva.setEstado("CONFIRMADA");
        reservaRepo.save(reserva);

        return "Reserva confirmada para " + user.getNombre() +
                " en la propiedad: " + prop.getDireccion();
    }

    @Override
    public List<Reserva> getReservas() {
        return reservaRepo.findAll();
    }

    @Override
    public Reserva findReserva(Long id) {
        // Buscamos por ID, si no existe devolvemos null
        return reservaRepo.findById(id).orElse(null);
    }

}