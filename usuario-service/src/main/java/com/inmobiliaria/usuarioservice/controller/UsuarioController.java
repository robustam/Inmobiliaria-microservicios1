package com.inmobiliaria.usuarioservice.controller;

import com.inmobiliaria.usuarioservice.client.PropiedadClient;
import com.inmobiliaria.usuarioservice.dto.PropiedadDTO;
import com.inmobiliaria.usuarioservice.model.Usuario;
import com.inmobiliaria.usuarioservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PropiedadClient propiedadClient;

    @GetMapping
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    // ✅ ENDPOINT NUEVO — requerido por reservas-service via Feign
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }

    @GetMapping("/ver-propiedades")
    public List<PropiedadDTO> verPropiedadesDesdeUsuario() {
        return propiedadClient.obtenerPropiedades();
    }
}
