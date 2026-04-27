package com.inmobiliaria.pagosservice.repository;

import com.inmobiliaria.pagosservice.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPagoRepository extends JpaRepository<Pago, Long> {
}