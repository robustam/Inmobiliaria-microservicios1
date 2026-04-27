package com.inmobiliaria.pagosservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data


public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;
    private Long idReserva;
    private Double monto;
    private String metodoPago; // "CREDITO", "DEBITO", "TRANSFERENCIA"
    private String estado; // "PENDIENTE", "REALIZADO", "RECHAZADO"
}