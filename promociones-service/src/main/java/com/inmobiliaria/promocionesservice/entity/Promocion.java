package com.inmobiliaria.promocionesservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "promociones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDescuento tipo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDescuento;

    @Column(precision = 10, scale = 2)
    private BigDecimal montoMinimo;

    private Integer cantidadUsoMaximos;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer cantidadUsoActuales;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    private String categoriaValida;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (activo == null) {
            activo = true;
        }
        if (cantidadUsoActuales == null) {
            cantidadUsoActuales = 0;
        }
    }

    /**
     * Valida si la promoción está vigente y disponible
     */
    public boolean esValida() {
        LocalDateTime ahora = LocalDateTime.now();
        return activo &&
                ahora.isAfter(fechaInicio) &&
                ahora.isBefore(fechaFin) &&
                (cantidadUsoMaximos == null || cantidadUsoActuales < cantidadUsoMaximos);
    }

    /**
     * Valida si cumple con monto mínimo
     */
    public boolean cumpleMontoMinimo(BigDecimal monto) {
        if (montoMinimo == null) {
            return true;
        }
        return monto.compareTo(montoMinimo) >= 0;
    }

    /**
     * Incrementa el contador de usos
     */
    public void incrementarUso() {
        this.cantidadUsoActuales++;
    }
}