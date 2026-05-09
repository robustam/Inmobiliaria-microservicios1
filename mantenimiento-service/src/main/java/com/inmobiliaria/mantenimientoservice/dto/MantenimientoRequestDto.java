package com.inmobiliaria.mantenimientoservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MantenimientoRequestDto {

    @NotNull(message = "El ID de propiedad es obligatorio")
    private Long idPropiedad;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String descripcion;

    @NotBlank(message = "La prioridad es obligatoria")
    private String prioridad;
}
