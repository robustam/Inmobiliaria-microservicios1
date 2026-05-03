package com.inmobiliaria.documentoservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    private Long idContrato;

    @NotNull(message = "El tipo de documento es obligatorio")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotBlank(message = "El nombre del documento es obligatorio")
    @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
    private String nombre;

    @NotBlank(message = "La URL del documento es obligatoria")
    private String url;

    private LocalDate fechaSubida;
    private Boolean firmado;

    public enum Tipo { CONTRATO, BOLETA, CERTIFICADO, INVENTARIO, GARANTIA, OTRO }
}
