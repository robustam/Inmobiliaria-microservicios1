package com.inmobiliaria.documentoservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.inmobiliaria.documentoservice.model.Documento.Tipo;

@Data
public class DocumentoRequestDto {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    private Long idContrato;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Tipo tipo;

    @NotBlank(message = "El nombre del documento es obligatorio")
    @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
    private String nombre;

    @NotBlank(message = "La URL es obligatoria")
    private String url;
}
