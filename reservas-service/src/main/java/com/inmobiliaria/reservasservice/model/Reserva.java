package com.inmobiliaria.reservasservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // <--- Esto genera los Getters automáticamente
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUsuario; // <--- Fíjate en las mayúsculas
    private Long idPropiedad;
    private String estado;
}