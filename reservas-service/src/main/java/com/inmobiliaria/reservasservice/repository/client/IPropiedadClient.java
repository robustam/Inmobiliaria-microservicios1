package com.inmobiliaria.reservasservice.repository.client;

import com.inmobiliaria.reservasservice.dto.PropiedadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "propiedad-service")
public interface IPropiedadClient {
    @GetMapping("/api/propiedades/{id}")
    PropiedadDTO buscarPropiedad(@PathVariable("id") Long id);
}