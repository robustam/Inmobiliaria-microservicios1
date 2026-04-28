package com.inmobiliaria.promocionesservice.dto;

import lombok.*;
import com.inmobiliaria.promocionesservice.entity.TipoDescuento;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearPromocionRequest {
    
    private String codigo;
    private String descripcion;
    private TipoDescuento tipo;
    private BigDecimal valorDescuento;
    private BigDecimal montoMinimo;
    private Integer cantidadUsoMaximos;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String categoriaValida;
}
