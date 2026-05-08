package com.inmobiliaria.propiedadservice.exception;

/**
 * Excepción para errores de lógica de negocio.
 * Se lanza cuando se incumple una regla de negocio.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
