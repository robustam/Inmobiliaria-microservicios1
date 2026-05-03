package com.inmobiliaria.mantenimientoservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "mantenimientos")
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de propiedad es obligatorio")
    private Long idPropiedad;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String descripcion;

    @NotNull(message = "La prioridad es obligatoria")
    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private LocalDate fechaSolicitud;
    private LocalDate fechaResolucion;

    @DecimalMin(value = "0.0", message = "El costo no puede ser negativo")
    private Double costo;

    public enum Prioridad { BAJA, MEDIA, ALTA, URGENTE }
    public enum Estado { PENDIENTE, EN_PROCESO, RESUELTO, RECHAZADO }
}
