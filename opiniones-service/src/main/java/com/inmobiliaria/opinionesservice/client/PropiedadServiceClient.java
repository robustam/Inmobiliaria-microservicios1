package com.inmobiliaria.opinionesservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.inmobiliaria.opinionesservice.dto.PropiedadDTO;  // ✅ AGREGAR

@FeignClient(name = "propiedad-service")
public interface PropiedadServiceClient {

    @GetMapping("/api/propiedades/{id}")
    PropiedadDTO getPropiedad(@PathVariable Long id);
}