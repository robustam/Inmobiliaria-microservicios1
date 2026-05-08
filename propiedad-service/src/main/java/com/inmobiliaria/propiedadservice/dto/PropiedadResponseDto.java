package com.inmobiliaria.propiedadservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuestas al cliente.
 * Expone solo los datos necesarios sin información interna.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropiedadResponseDto {

    private Long id;
    private String direccion;
    private Integer habitaciones;
    private Double precio;
}
