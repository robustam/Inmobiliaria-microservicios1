package com.inmobiliaria.usuarioservice.controller;

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

    @GetMapping
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }
}