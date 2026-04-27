package com.inmobiliaria.usuarioservice.dto;

import lombok.Data;

@Data
public class PropiedadDTO {
    private Long id;
    private String direccion;
    private double precio;
}