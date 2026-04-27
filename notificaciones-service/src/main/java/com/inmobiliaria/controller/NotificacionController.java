package com.inmobiliaria.notificacionesservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.inmobiliaria.notificacionesservice.entity.Notificacion;
import com.inmobiliaria.notificacionesservice.service.NotificacionService;
import com.inmobiliaria.notificacionesservice.dto.EnviarNotificacionRequest;
import com.inmobiliaria.notificacionesservice.dto.NotificacionResponse;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    /**
     * Endpoint para enviar una notificación
     * POST /api/notificaciones/enviar
     */
    @PostMapping("/enviar")
    public ResponseEntity<NotificacionResponse> enviarNotificacion(
            @RequestBody EnviarNotificacionRequest request) {
        NotificacionResponse response = notificacionService.enviarNotificacion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint para obtener una notificación por ID
     * GET /api/notificaciones/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtenerNotificacion(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.obtenerNotificacion(id);
        return ResponseEntity.ok(notificacion);
    }

    /**
     * Endpoint para obtener todas las notificaciones de un usuario
     * GET /api/notificaciones/usuario/{idUsuario}
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorUsuario(
            @PathVariable Long idUsuario) {
        List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorUsuario(idUsuario);
        return ResponseEntity.ok(notificaciones);
    }

    /**
     * Endpoint para obtener todas las notificaciones de una reserva
     * GET /api/notificaciones/reserva/{idReserva}
     */
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorReserva(
            @PathVariable Long idReserva) {
        List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorReserva(idReserva);
        return ResponseEntity.ok(notificaciones);
    }

    /**
     * Endpoint para obtener todas las notificaciones
     * GET /api/notificaciones
     */
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerTodasLasNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.obtenerTodasLasNotificaciones();
        return ResponseEntity.ok(notificaciones);
    }
}
