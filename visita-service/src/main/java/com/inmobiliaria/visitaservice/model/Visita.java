package com.inmobiliaria.visitaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "visitas")
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El ID de propiedad es obligatorio")
    private Long idPropiedad;

    @NotNull(message = "La fecha y hora es obligatoria")
    private LocalDateTime fechaHora;

    @Size(max = 500, message = "Las observaciones no pueden superar 500 caracteres")
    private String observaciones;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public enum Estado { PENDIENTE, CONFIRMADA, REALIZADA, CANCELADA }
}
