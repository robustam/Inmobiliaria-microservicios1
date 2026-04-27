package com.inmobiliaria.opinionesservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.inmobiliaria.opinionesservice.dto.UsuarioDTO;  // ✅ IMPORT

@FeignClient(name = "usuario-service")
public interface UsuarioServiceClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioDTO getUsuario(@PathVariable Long id);
}