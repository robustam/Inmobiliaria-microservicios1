package com.inmobiliaria.usuarioservice.controller;

import com.inmobiliaria.usuarioservice.client.PropiedadClient;
import com.inmobiliaria.usuarioservice.dto.PropiedadDTO;
import com.inmobiliaria.usuarioservice.model.Usuario;
import com.inmobiliaria.usuarioservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PropiedadClient propiedadClient; // Inyectamos nuestro teléfono

    @GetMapping
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }

    // NUEVO ENDPOINT: El usuario pide ver las propiedades
    @GetMapping("/ver-propiedades")
    public List<PropiedadDTO> verPropiedadesDesdeUsuario() {
        // ¡Aquí ocurre la magia de la comunicación interna!
        return propiedadClient.obtenerPropiedades();
    }
}
