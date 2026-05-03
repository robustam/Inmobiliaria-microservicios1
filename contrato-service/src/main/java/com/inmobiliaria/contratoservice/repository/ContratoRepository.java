package com.inmobiliaria.contratoservice.repository;
import com.inmobiliaria.contratoservice.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByIdUsuario(Long idUsuario);
    List<Contrato> findByIdPropiedad(Long idPropiedad);
}
