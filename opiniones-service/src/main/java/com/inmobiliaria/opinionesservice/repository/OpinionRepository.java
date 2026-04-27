package com.inmobiliaria.opinionesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inmobiliaria.opinionesservice.model.Opinion;
import java.util.List;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    List<Opinion> findByIdPropiedad(Long idPropiedad);
    List<Opinion> findByIdUsuario(Long idUsuario);
}
