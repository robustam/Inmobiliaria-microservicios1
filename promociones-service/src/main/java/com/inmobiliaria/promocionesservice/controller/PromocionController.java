package com.inmobiliaria.promocionesservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.inmobiliaria.promocionesservice.entity.Promocion;
import com.inmobiliaria.promocionesservice.service.PromocionService;
import com.inmobiliaria.promocionesservice.dto.ValidarPromocionRequest;
import com.inmobiliaria.promocionesservice.dto.ValidarPromocionResponse;
import com.inmobiliaria.promocionesservice.dto.CrearPromocionRequest;
import java.util.List;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    /**
     * Endpoint para validar un código de promoción
     * POST /api/promociones/validar
     */
    @PostMapping("/validar")
    public ResponseEntity<ValidarPromocionResponse> validarPromocion(
            @RequestBody ValidarPromocionRequest request) {
        ValidarPromocionResponse response = promocionService.validarPromocion(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para registrar el uso de una promoción
     * POST /api/promociones/usar/{codigo}
     */
    @PostMapping("/usar/{codigo}")
    public ResponseEntity<Void> registrarUsoPromocion(@PathVariable String codigo) {
        promocionService.registrarUsoPromocion(codigo);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para obtener promociones activas
     * GET /api/promociones/activas
     */
    @GetMapping("/activas")
    public ResponseEntity<List<Promocion>> obtenerPromocionesActivas() {
        List<Promocion> promociones = promocionService.obtenerPromocionesActivas();
        return ResponseEntity.ok(promociones);
    }

    /**
     * Endpoint para obtener una promoción por código
     * GET /api/promociones/{codigo}
     */
    @GetMapping("/{codigo}")
    public ResponseEntity<Promocion> obtenerPromocion(@PathVariable String codigo) {
        Promocion promocion = promocionService.obtenerPromocion(codigo);
        return ResponseEntity.ok(promocion);
    }

    /**
     * Endpoint para obtener todas las promociones
     * GET /api/promociones
     */
    @GetMapping
    public ResponseEntity<List<Promocion>> obtenerTodasLasPromociones() {
        List<Promocion> promociones = promocionService.obtenerTodasLasPromociones();
        return ResponseEntity.ok(promociones);
    }

    /**
     * Endpoint para crear una nueva promoción (ADMIN)
     * POST /api/promociones/crear
     */
    @PostMapping("/crear")
    public ResponseEntity<Promocion> crearPromocion(
            @RequestBody CrearPromocionRequest request) {
        Promocion promocion = promocionService.crearPromocion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(promocion);
    }
}