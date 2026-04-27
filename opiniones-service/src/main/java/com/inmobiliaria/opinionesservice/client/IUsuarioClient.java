package com.inmobiliaria.opinionesservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// El "name" DEBE ser exactamente igual al spring.application.name del servicio destino
@FeignClient(name = "usuario-service")
public interface IUsuarioClient {

    // Este endpoint debe existir en tu UsuarioController
    @GetMapping("/api/usuarios/{id}")
    Object obtenerUsuarioPorId(@PathVariable("id") Long id);
}