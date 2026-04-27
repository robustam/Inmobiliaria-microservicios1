package com.inmobiliaria.pagosservice.dto;

import lombok.Data;

@Data
public class ReservaDTO {
    private Long id;
    private Long idUsuario;
    private Long idPropiedad;
    private String estado;
}
