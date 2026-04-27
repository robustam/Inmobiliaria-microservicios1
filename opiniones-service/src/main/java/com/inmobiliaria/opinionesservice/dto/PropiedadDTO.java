package com.inmobiliaria.opinionesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropiedadDTO {

    private Long id;
    private String titulo;
    private String direccion;
    private Double precio;
}
