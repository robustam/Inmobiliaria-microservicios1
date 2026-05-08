package com.inmobiliaria.propiedadservice.exception;

/**
 * Excepción lanzada cuando un recurso no se encuentra en la base de datos.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
