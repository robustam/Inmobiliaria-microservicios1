package com.inmobiliaria.notificacionesservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idUsuario;

    private Long idReserva;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo;

    @Column(nullable = false, length = 255)
    private String asunto;

    @Column(columnDefinition = "TEXT")
    private String cuerpo;

    @Column(nullable = false)
    private String emailDestinario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoNotificacion estado;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaEnvio;

    private LocalDateTime fechaLectura;

    @PrePersist
    protected void onCreate() {
        fechaEnvio = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoNotificacion.ENVIADO;
        }
    }
}
