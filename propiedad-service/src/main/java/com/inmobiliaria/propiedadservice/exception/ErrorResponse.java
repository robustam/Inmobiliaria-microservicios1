package com.inmobiliaria.propiedadservice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Estructura estándar para las respuestas de error.
 * Se utiliza en toda la aplicación para mantener consistencia.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /**
     * Timestamp del error.
     */
    private LocalDateTime timestamp;

    /**
     * Código HTTP del error.
     */
    private int status;

    /**
     * Tipo de error (ejemplo: "Validación fallida", "Recurso no encontrado").
     */
    private String error;

    /**
     * Mensaje descriptivo del error.
     */
    private String message;

    /**
     * Ruta del endpoint que generó el error.
     */
    private String path;

    /**
     * Errores de validación detallados por campo (solo cuando aplica).
     */
    private Map<String, String> validationErrors;
}
