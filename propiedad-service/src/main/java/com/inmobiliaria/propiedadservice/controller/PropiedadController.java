package com.inmobiliaria.propiedadservice.controller;

import com.inmobiliaria.propiedadservice.dto.PropiedadRequestDto;
import com.inmobiliaria.propiedadservice.dto.PropiedadResponseDto;
import com.inmobiliaria.propiedadservice.service.PropiedadService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el manejo de propiedades.
 * Define los endpoints de la API siguiendo principios RESTful.
 */
@Slf4j
@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    @Autowired
    private PropiedadService service;

    /**
     * GET /api/propiedades
     * Obtiene todas las propiedades disponibles.
     *
     * @return Lista de propiedades con código 200 OK
     */
    @GetMapping
    public ResponseEntity<List<PropiedadResponseDto>> listarTodas() {
        log.info("GET /api/propiedades - Listando todas las propiedades");
        List<PropiedadResponseDto> propiedades = service.obtenerTodas();
        log.info("Se encontraron {} propiedades", propiedades.size());
        return ResponseEntity.ok(propiedades);
    }

    /**
     * GET /api/propiedades/{id}
     * Obtiene una propiedad específica por su ID.
     *
     * @param id ID de la propiedad
     * @return Propiedad encontrada con código 200 OK, o 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadResponseDto> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/propiedades/{} - Buscando propiedad", id);
        PropiedadResponseDto propiedad = service.obtenerPorId(id);
        return ResponseEntity.ok(propiedad);
    }

    /**
     * POST /api/propiedades
     * Crea una nueva propiedad.
     *
     * @param requestDto Datos de la propiedad a crear
     * @return Propiedad creada con código 201 Created
     */
    @PostMapping
    public ResponseEntity<PropiedadResponseDto> crear(@Valid @RequestBody PropiedadRequestDto requestDto) {
        log.info("POST /api/propiedades - Creando propiedad: {}", requestDto.getDireccion());
        PropiedadResponseDto propiedad = service.crear(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(propiedad);
    }

    /**
     * PUT /api/propiedades/{id}
     * Actualiza una propiedad existente.
     *
     * @param id ID de la propiedad a actualizar
     * @param requestDto Nuevos datos de la propiedad
     * @return Propiedad actualizada con código 200 OK, o 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<PropiedadResponseDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PropiedadRequestDto requestDto) {
        log.info("PUT /api/propiedades/{} - Actualizando propiedad", id);
        PropiedadResponseDto propiedad = service.actualizar(id, requestDto);
        return ResponseEntity.ok(propiedad);
    }

    /**
     * DELETE /api/propiedades/{id}
     * Elimina una propiedad existente.
     *
     * @param id ID de la propiedad a eliminar
     * @return Código 204 No Content, o 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/propiedades/{} - Eliminando propiedad", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
