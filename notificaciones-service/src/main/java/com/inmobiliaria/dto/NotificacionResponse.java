package com.inmobiliaria.notificacionesservice.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionResponse {

    private Long id;
    private String estado;
    private String mensaje;
    private LocalDateTime fechaEnvio;
}
