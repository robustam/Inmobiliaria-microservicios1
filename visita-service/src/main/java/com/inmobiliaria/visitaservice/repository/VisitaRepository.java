package com.inmobiliaria.visitaservice.repository;
import com.inmobiliaria.visitaservice.model.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VisitaRepository extends JpaRepository<Visita, Long> {
    List<Visita> findByIdUsuario(Long idUsuario);
    List<Visita> findByIdPropiedad(Long idPropiedad);
}
