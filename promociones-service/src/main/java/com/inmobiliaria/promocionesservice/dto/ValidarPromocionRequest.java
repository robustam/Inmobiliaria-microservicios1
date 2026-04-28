package com.inmobiliaria.promocionesservice.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidarPromocionRequest {

    private String codigo;
    private BigDecimal montoReserva;
    private Long idUsuario;
    private String tipoPropiedad;
}