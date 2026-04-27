package com.inmobiliaria.notificacionesservice.dto;

import lombok.*;
import com.inmobiliaria.notificacionesservice.entity.TipoNotificacion;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnviarNotificacionRequest {

    private Long idUsuario;
    private Long idReserva;
    private TipoNotificacion tipo;
    private String asunto;
    private String cuerpo;
    private String emailDestinario;
}
