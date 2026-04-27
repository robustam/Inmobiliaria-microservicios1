package com.inmobiliaria.reservasservice.service;

import com.inmobiliaria.reservasservice.model.Reserva;
import java.util.List;

public interface IReservaService {
    String saveReserva(Reserva reserva);
    List<Reserva> getReservas();
    Reserva findReserva(Long id); // <--- AÑADE ESTA LÍNEA
}