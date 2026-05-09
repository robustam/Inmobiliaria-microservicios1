package com.inmobiliaria.pagosservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PagoRequestDto {

    @NotNull(message = "El ID de reserva es obligatorio")
    private Long idReserva;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;
}
