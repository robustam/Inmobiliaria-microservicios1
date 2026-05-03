package com.inmobiliaria.documentoservice.repository;
import com.inmobiliaria.documentoservice.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByIdUsuario(Long idUsuario);
    List<Documento> findByIdContrato(Long idContrato);
    List<Documento> findByTipo(Documento.Tipo tipo);
}
