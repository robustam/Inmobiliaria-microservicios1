package com.inmobiliaria.notificacionesservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.inmobiliaria.notificacionesservice.entity.Notificacion;
import com.inmobiliaria.notificacionesservice.service.NotificacionService;
import com.inmobiliaria.notificacionesservice.dto.EnviarNotificacionRequest;
import com.inmobiliaria.notificacionesservice.dto.NotificacionResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/enviar")
    public ResponseEntity<NotificacionResponse> enviarNotificacion(
            @RequestBody EnviarNotificacionRequest request) {
        log.info("POST /api/notificaciones/enviar - Usuario: {}, Tipo: {}", request.getIdUsuario(), request.getTipo());
        NotificacionResponse response = notificacionService.enviarNotificacion(request);
        log.info("Notificación enviada exitosamente ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerNotificacion(@PathVariable Long id) {
        log.info("GET /api/notificaciones/{}", id);
        try {
            Notificacion notificacion = notificacionService.obtenerNotificacion(id);
            if (notificacion == null) {
                log.error("Notificación ID {} no encontrada", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(notificacion);
        } catch (Exception e) {
            log.error("Error al obtener notificación ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorUsuario(@PathVariable Long idUsuario) {
        log.info("GET /api/notificaciones/usuario/{}", idUsuario);
        List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorUsuario(idUsuario);
        log.debug("Total notificaciones para usuario {}: {}", idUsuario, notificaciones.size());
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorReserva(@PathVariable Long idReserva) {
        log.info("GET /api/notificaciones/reserva/{}", idReserva);
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesPorReserva(idReserva));
    }

    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerTodasLasNotificaciones() {
        log.info("GET /api/notificaciones - Listando todas");
        return ResponseEntity.ok(notificacionService.obtenerTodasLasNotificaciones());
    }
}
