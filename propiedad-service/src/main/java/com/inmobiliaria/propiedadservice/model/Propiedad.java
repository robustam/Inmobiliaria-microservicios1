package com.inmobiliaria.propiedadservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Propiedad para la persistencia en base de datos.
 * Esta clase se mapea con la tabla 'propiedades'.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "propiedades")
public class Propiedad {

    /**
     * Identificador único de la propiedad.
     * Se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Dirección física de la propiedad.
     */
    @Column(nullable = false, length = 200)
    private String direccion;

    /**
     * Número de habitaciones de la propiedad.
     */
    @Column(nullable = false)
    private Integer habitaciones;

    /**
     * Precio de alquiler mensual en moneda local.
     */
    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double precio;
}
