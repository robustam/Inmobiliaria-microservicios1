package com.inmobiliaria.notificacionesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inmobiliaria.notificacionesservice.entity.Notificacion;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByIdUsuario(Long idUsuario);
    List<Notificacion> findByIdReserva(Long idReserva);
    List<Notificacion> findByEmailDestinario(String emailDestinario);
}
