package com.inmobiliaria.promocionesservice.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidarPromocionResponse {
    
    private String codigo;
    private boolean valido;
    private String mensaje;
    private String tipoDescuento;
    private BigDecimal porcentajeDescuento;
    private BigDecimal montoDescuento;
    private BigDecimal montoFinal;
}
