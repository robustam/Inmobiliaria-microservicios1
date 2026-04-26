package com.inmobiliaria.propiedadservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="propiedades")

public class Propiedad {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

private String direccion;
private int habitaciones;
private double precio;


}
