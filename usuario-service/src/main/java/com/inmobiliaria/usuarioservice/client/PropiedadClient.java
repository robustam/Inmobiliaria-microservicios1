package com.inmobiliaria.usuarioservice.client;

import com.inmobiliaria.usuarioservice.dto.PropiedadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "propiedad-service")
public interface PropiedadClient {

    @GetMapping("/api/propiedades")
    List<PropiedadDTO> obtenerPropiedades();
}