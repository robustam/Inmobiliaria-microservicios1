package com.inmobiliaria.propiedadservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "propiedades")
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    private String direccion;

    @Min(value = 1, message = "Debe tener al menos 1 habitación")
    @Max(value = 20, message = "No puede tener más de 20 habitaciones")
    private int habitaciones;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double precio;
}
