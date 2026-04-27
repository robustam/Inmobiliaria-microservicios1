package com.inmobiliaria.notificacionesservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inmobiliaria.notificacionesservice.entity.Notificacion;
import com.inmobiliaria.notificacionesservice.entity.EstadoNotificacion;
import com.inmobiliaria.notificacionesservice.repository.NotificacionRepository;
import com.inmobiliaria.notificacionesservice.dto.EnviarNotificacionRequest;
import com.inmobiliaria.notificacionesservice.dto.NotificacionResponse;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    /**
     * Envía una notificación (simula el envío guardando en H2)
     */
    public NotificacionResponse enviarNotificacion(EnviarNotificacionRequest request) {
        try {
            // Crear la notificación
            Notificacion notificacion = Notificacion.builder()
                    .idUsuario(request.getIdUsuario())
                    .idReserva(request.getIdReserva())
                    .tipo(request.getTipo())
                    .asunto(request.getAsunto())
                    .cuerpo(request.getCuerpo())
                    .emailDestinario(request.getEmailDestinario())
                    .estado(EstadoNotificacion.ENVIADO)
                    .build();

            // Guardar en H2 (simula el envío)
            Notificacion guardada = notificacionRepository.save(notificacion);

            // Responder al cliente
            return NotificacionResponse.builder()
                    .id(guardada.getId())
                    .estado(guardada.getEstado().toString())
                    .mensaje("Notificación enviada correctamente a " + request.getEmailDestinario())
                    .fechaEnvio(guardada.getFechaEnvio())
                    .build();

        } catch (Exception e) {
            // Si hay error, guardar con estado FALLIDO
            Notificacion notificacion = Notificacion.builder()
                    .idUsuario(request.getIdUsuario())
                    .idReserva(request.getIdReserva())
                    .tipo(request.getTipo())
                    .asunto(request.getAsunto())
                    .cuerpo(request.getCuerpo())
                    .emailDestinario(request.getEmailDestinario())
                    .estado(EstadoNotificacion.FALLIDO)
                    .build();

            Notificacion guardada = notificacionRepository.save(notificacion);

            return NotificacionResponse.builder()
                    .id(guardada.getId())
                    .estado(EstadoNotificacion.FALLIDO.toString())
                    .mensaje("Error al enviar notificación: " + e.getMessage())
                    .fechaEnvio(guardada.getFechaEnvio())
                    .build();
        }
    }

    /**
     * Obtiene una notificación por ID
     */
    public Notificacion obtenerNotificacion(Long id) {
        return notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
    }

    /**
     * Obtiene todas las notificaciones de un usuario
     */
    public List<Notificacion> obtenerNotificacionesPorUsuario(Long idUsuario) {
        return notificacionRepository.findByIdUsuario(idUsuario);
    }

    /**
     * Obtiene todas las notificaciones de una reserva
     */
    public List<Notificacion> obtenerNotificacionesPorReserva(Long idReserva) {
        return notificacionRepository.findByIdReserva(idReserva);
    }

    /**
     * Obtiene todas las notificaciones
     */
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionRepository.findAll();
    }
}
