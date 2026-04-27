package com.inmobiliaria.reservasservice.repository.client;

import com.inmobiliaria.reservasservice.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service")
public interface IUsuarioClient {
    // Asegúrate de que esta ruta coincida con el Controller de tu usuario-service
    @GetMapping("/api/usuarios/{id}")
    UsuarioDTO buscarUsuario(@PathVariable("id") Long id);
}