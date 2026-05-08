package com.inmobiliaria.propiedadservice.service;

import com.inmobiliaria.propiedadservice.dto.PropiedadRequestDto;
import com.inmobiliaria.propiedadservice.dto.PropiedadResponseDto;
import com.inmobiliaria.propiedadservice.exception.ResourceNotFoundException;
import com.inmobiliaria.propiedadservice.model.Propiedad;
import com.inmobiliaria.propiedadservice.repository.PropiedadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de lógica de negocio para Propiedades.
 * Maneja las operaciones CRUD y la conversión DTO-Entity.
 */
@Slf4j
@Service
@Transactional
public class PropiedadService {

    @Autowired
    private PropiedadRepository repository;

    /**
     * Obtiene todas las propiedades.
     *
     * @return Lista de DTOs de respuesta
     */
    public List<PropiedadResponseDto> obtenerTodas() {
        log.info("Obteniendo todas las propiedades");
        return repository.findAll().stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una propiedad por ID.
     *
     * @param id ID de la propiedad
     * @return DTO de respuesta
     * @throws ResourceNotFoundException si la propiedad no existe
     */
    public PropiedadResponseDto obtenerPorId(Long id) {
        log.info("Obteniendo propiedad con ID: {}", id);
        return repository.findById(id)
                .map(p -> {
                    log.info("Propiedad encontrada: {}", p.getDireccion());
                    return entityToResponseDto(p);
                })
                .orElseThrow(() -> {
                    log.error("Propiedad con ID {} no encontrada", id);
                    return new ResourceNotFoundException("Propiedad con ID " + id + " no encontrada");
                });
    }

    /**
     * Crea una nueva propiedad.
     *
     * @param requestDto DTO con los datos de la propiedad
     * @return DTO de respuesta con la propiedad creada
     */
    public PropiedadResponseDto crear(PropiedadRequestDto requestDto) {
        log.info("Creando propiedad: {}", requestDto.getDireccion());

        Propiedad propiedad = new Propiedad();
        propiedad.setDireccion(requestDto.getDireccion());
        propiedad.setHabitaciones(requestDto.getHabitaciones());
        propiedad.setPrecio(requestDto.getPrecio());

        Propiedad saved = repository.save(propiedad);
        log.info("Propiedad creada exitosamente con ID: {}", saved.getId());

        return entityToResponseDto(saved);
    }

    /**
     * Actualiza una propiedad existente.
     *
     * @param id ID de la propiedad a actualizar
     * @param requestDto DTO con los nuevos datos
     * @return DTO de respuesta con la propiedad actualizada
     * @throws ResourceNotFoundException si la propiedad no existe
     */
    public PropiedadResponseDto actualizar(Long id, PropiedadRequestDto requestDto) {
        log.info("Actualizando propiedad con ID: {}", id);

        Propiedad propiedad = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Propiedad con ID {} no encontrada para actualizar", id);
                    return new ResourceNotFoundException("Propiedad con ID " + id + " no encontrada");
                });

        propiedad.setDireccion(requestDto.getDireccion());
        propiedad.setHabitaciones(requestDto.getHabitaciones());
        propiedad.setPrecio(requestDto.getPrecio());

        Propiedad updated = repository.save(propiedad);
        log.info("Propiedad actualizada exitosamente con ID: {}", updated.getId());

        return entityToResponseDto(updated);
    }

    /**
     * Elimina una propiedad.
     *
     * @param id ID de la propiedad a eliminar
     * @throws ResourceNotFoundException si la propiedad no existe
     */
    public void eliminar(Long id) {
        log.info("Eliminando propiedad con ID: {}", id);

        if (!repository.existsById(id)) {
            log.error("Propiedad con ID {} no encontrada para eliminar", id);
            throw new ResourceNotFoundException("Propiedad con ID " + id + " no encontrada");
        }

        repository.deleteById(id);
        log.info("Propiedad eliminada exitosamente");
    }

    /**
     * Convierte una entidad Propiedad a PropiedadResponseDto.
     *
     * @param propiedad Entidad de la base de datos
     * @return DTO de respuesta
     */
    private PropiedadResponseDto entityToResponseDto(Propiedad propiedad) {
        return PropiedadResponseDto.builder()
                .id(propiedad.getId())
                .direccion(propiedad.getDireccion())
                .habitaciones(propiedad.getHabitaciones())
                .precio(propiedad.getPrecio())
                .build();
    }
}
