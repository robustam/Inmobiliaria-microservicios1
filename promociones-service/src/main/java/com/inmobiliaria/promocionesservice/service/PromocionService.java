package com.inmobiliaria.promocionesservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inmobiliaria.promocionesservice.entity.Promocion;
import com.inmobiliaria.promocionesservice.entity.TipoDescuento;
import com.inmobiliaria.promocionesservice.repository.PromocionRepository;
import com.inmobiliaria.promocionesservice.dto.ValidarPromocionRequest;
import com.inmobiliaria.promocionesservice.dto.ValidarPromocionResponse;
import com.inmobiliaria.promocionesservice.dto.CrearPromocionRequest;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    /**
     * Valida un código de promoción y calcula el descuento
     */
    public ValidarPromocionResponse validarPromocion(ValidarPromocionRequest request) {
        // Buscar la promoción
        var promocionOpt = promocionRepository.findByCodigo(request.getCodigo());

        if (promocionOpt.isEmpty()) {
            return ValidarPromocionResponse.builder()
                    .codigo(request.getCodigo())
                    .valido(false)
                    .mensaje("Código no encontrado")
                    .tipoDescuento(null)
                    .porcentajeDescuento(BigDecimal.ZERO)
                    .montoDescuento(BigDecimal.ZERO)
                    .montoFinal(request.getMontoReserva())
                    .build();
        }

        Promocion promocion = promocionOpt.get();

        // Validar que la promoción esté vigente
        if (!promocion.esValida()) {
            return ValidarPromocionResponse.builder()
                    .codigo(request.getCodigo())
                    .valido(false)
                    .mensaje("Código expirado o no disponible")
                    .tipoDescuento(null)
                    .porcentajeDescuento(BigDecimal.ZERO)
                    .montoDescuento(BigDecimal.ZERO)
                    .montoFinal(request.getMontoReserva())
                    .build();
        }

        // Validar monto mínimo
        if (!promocion.cumpleMontoMinimo(request.getMontoReserva())) {
            return ValidarPromocionResponse.builder()
                    .codigo(request.getCodigo())
                    .valido(false)
                    .mensaje("No cumple con el monto mínimo de " + promocion.getMontoMinimo())
                    .tipoDescuento(null)
                    .porcentajeDescuento(BigDecimal.ZERO)
                    .montoDescuento(BigDecimal.ZERO)
                    .montoFinal(request.getMontoReserva())
                    .build();
        }

        // Calcular el descuento
        BigDecimal montoDescuento;
        BigDecimal porcentajeDescuento = BigDecimal.ZERO;

        if (promocion.getTipo() == TipoDescuento.PORCENTAJE) {
            // Descuento porcentual
            porcentajeDescuento = promocion.getValorDescuento();
            montoDescuento = request.getMontoReserva()
                    .multiply(promocion.getValorDescuento())
                    .divide(new BigDecimal(100));
        } else {
            // Descuento monto fijo
            montoDescuento = promocion.getValorDescuento();
        }

        BigDecimal montoFinal = request.getMontoReserva().subtract(montoDescuento);

        return ValidarPromocionResponse.builder()
                .codigo(request.getCodigo())
                .valido(true)
                .mensaje("Código válido - Descuento aplicado")
                .tipoDescuento(promocion.getTipo().toString())
                .porcentajeDescuento(porcentajeDescuento)
                .montoDescuento(montoDescuento)
                .montoFinal(montoFinal)
                .build();
    }

    /**
     * Registra el uso de una promoción
     */
    public void registrarUsoPromocion(String codigo) {
        var promocionOpt = promocionRepository.findByCodigo(codigo);

        if (promocionOpt.isPresent()) {
            Promocion promocion = promocionOpt.get();
            promocion.incrementarUso();
            promocionRepository.save(promocion);
        }
    }

    /**
     * Obtiene todas las promociones activas
     */
    public List<Promocion> obtenerPromocionesActivas() {
        return promocionRepository.findByActivoTrue();
    }

    /**
     * Obtiene una promoción por código
     */
    public Promocion obtenerPromocion(String codigo) {
        return promocionRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
    }

    /**
     * Obtiene una promoción por ID
     */
    public Promocion obtenerPromocionPorId(Long id) {
        return promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
    }

    /**
     * Crea una nueva promoción (ADMIN)
     */
    public Promocion crearPromocion(CrearPromocionRequest request) {
        Promocion promocion = Promocion.builder()
                .codigo(request.getCodigo())
                .descripcion(request.getDescripcion())
                .tipo(request.getTipo())
                .valorDescuento(request.getValorDescuento())
                .montoMinimo(request.getMontoMinimo())
                .cantidadUsoMaximos(request.getCantidadUsoMaximos())
                .cantidadUsoActuales(0)
                .activo(true)
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .categoriaValida(request.getCategoriaValida())
                .build();

        return promocionRepository.save(promocion);
    }

    /**
     * Obtiene todas las promociones
     */
    public List<Promocion> obtenerTodasLasPromociones() {
        return promocionRepository.findAll();
    }
}