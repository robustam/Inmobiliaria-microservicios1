package com.inmobiliaria.mantenimientoservice.repository;
import com.inmobiliaria.mantenimientoservice.model.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
    List<Mantenimiento> findByIdPropiedad(Long idPropiedad);
    List<Mantenimiento> findByEstado(Mantenimiento.Estado estado);
}
