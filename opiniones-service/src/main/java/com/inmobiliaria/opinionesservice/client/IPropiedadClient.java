package com.inmobiliaria.opinionesservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "propiedad-service")
public interface IPropiedadClient {

    // Este endpoint debe existir en tu PropiedadController
    @GetMapping("/api/propiedades/{id}")
    Object obtenerPropiedadPorId(@PathVariable("id") Long id);
}