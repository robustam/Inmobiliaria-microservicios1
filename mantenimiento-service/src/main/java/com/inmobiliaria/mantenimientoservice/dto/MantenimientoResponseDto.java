package com.inmobiliaria.mantenimientoservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MantenimientoResponseDto {
    private Long id;
    private Long idPropiedad;
    private Long idUsuario;
    private String descripcion;
    private String prioridad;
    private String estado;
    private LocalDate fechaSolicitud;
    private LocalDate fechaResolucion;
    private Double costo;
}
