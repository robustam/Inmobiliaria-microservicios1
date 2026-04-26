package com.inmobiliaria.propiedadservice.repository;

import com.inmobiliaria.propiedadservice.model.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
}//