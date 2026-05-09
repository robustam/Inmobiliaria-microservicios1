package com.inmobiliaria.authservice.dto;

import lombok.Data;
import com.inmobiliaria.authservice.model.Usuario.Rol;

@Data
public class UsuarioResponseDto {
    private Long id;
    private String nombre;
    private String email;
    private Rol rol;
    private Boolean activo;
}
