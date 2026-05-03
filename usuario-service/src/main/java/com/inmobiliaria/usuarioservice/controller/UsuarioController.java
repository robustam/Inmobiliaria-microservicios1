package com.inmobiliaria.usuarioservice.controller;

import com.inmobiliaria.usuarioservice.client.PropiedadClient;
import com.inmobiliaria.usuarioservice.dto.PropiedadDTO;
import com.inmobiliaria.usuarioservice.model.Usuario;
import com.inmobiliaria.usuarioservice.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired private UsuarioRepository repository;
    @Autowired private PropiedadClient propiedadClient;

    @GetMapping
    public List<Usuario> listarTodos() {
        log.info("GET /api/usuarios - Listando todos los usuarios");
        List<Usuario> usuarios = repository.findAll();
        log.debug("Total usuarios encontrados: {}", usuarios.size());
        return usuarios;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/usuarios/{} - Buscando usuario por ID", id);
        return repository.findById(id)
                .map(u -> { log.info("Usuario encontrado: {}", u.getNombre()); return ResponseEntity.ok(u); })
                .orElseGet(() -> { log.error("Usuario con ID {} no encontrado", id); return ResponseEntity.notFound().build(); });
    }

    @PostMapping
    public ResponseEntity<Usuario> guardar(@Valid @RequestBody Usuario usuario) {
        log.info("POST /api/usuarios - Creando usuario: {}", usuario.getNombre());
        Usuario saved = repository.save(usuario);
        log.info("Usuario creado con ID: {}", saved.getId());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/ver-propiedades")
    public List<PropiedadDTO> verPropiedadesDesdeUsuario() {
        log.info("GET /api/usuarios/ver-propiedades - Consultando propiedades via Feign");
        return propiedadClient.obtenerPropiedades();
    }
}
