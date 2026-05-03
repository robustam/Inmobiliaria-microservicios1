package com.inmobiliaria.contratoservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El ID de propiedad es obligatorio")
    private Long idPropiedad;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotNull(message = "El monto mensual es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto mensual debe ser mayor a 0")
    private Double montoMensual;

    @DecimalMin(value = "0.0", message = "La garantía no puede ser negativa")
    private Double garantia;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public enum Estado { ACTIVO, TERMINADO, CANCELADO }
}
