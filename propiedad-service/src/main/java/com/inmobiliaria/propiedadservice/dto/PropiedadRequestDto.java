package com.inmobiliaria.propiedadservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir solicitudes de creación/actualización de propiedades.
 * Separa la validación de entrada de la entidad de persistencia.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadRequestDto {

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    private String direccion;

    @NotNull(message = "El número de habitaciones es obligatorio")
    @Min(value = 1, message = "Debe tener al menos 1 habitación")
    @Max(value = 20, message = "No puede tener más de 20 habitaciones")
    private Integer habitaciones;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @DecimalMax(value = "999999999.99", message = "El precio es demasiado alto")
    private Double precio;
}
