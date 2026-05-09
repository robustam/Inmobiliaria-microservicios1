package com.inmobiliaria.reservasservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El ID de propiedad es obligatorio")
    private Long idPropiedad;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
