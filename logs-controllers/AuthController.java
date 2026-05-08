package com.inmobiliaria.authservice.controller;

import com.inmobiliaria.authservice.model.Usuario;
import com.inmobiliaria.authservice.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UsuarioRepository repository;

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Usuario usuario) {
        log.info("POST /api/auth/registro - Email: {}, Rol: {}", usuario.getEmail(), usuario.getRol());
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            log.error("Registro fallido - Email ya existe: {}", usuario.getEmail());
            return ResponseEntity.badRequest().body("Email ya registrado");
        }
        usuario.setActivo(true);
        Usuario saved = repository.save(usuario);
        log.info("Usuario registrado con ID: {}", saved.getId());
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        log.info("POST /api/auth/login - Intento de login: {}", email);
        return repository.findByEmail(email)
                .filter(u -> u.getPassword().equals(credenciales.get("password")) && u.getActivo())
                .map(u -> {
                    log.info("Login exitoso para: {} con rol: {}", email, u.getRol());
                    return ResponseEntity.ok(Map.of("mensaje", "Login exitoso", "id", u.getId(), "nombre", u.getNombre(), "rol", u.getRol().toString()));
                })
                .orElseGet(() -> {
                    log.error("Login fallido para: {}", email);
                    return ResponseEntity.status(401).build();
                });
    }

    @GetMapping("/usuarios")
    public List<Usuario> listar() {
        log.info("GET /api/auth/usuarios - Listando usuarios");
        return repository.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/auth/usuarios/{} - Buscando usuario", id);
        return repository.findById(id)
                .map(u -> { log.info("Usuario encontrado: {}", u.getNombre()); return ResponseEntity.ok(u); })
                .orElseGet(() -> { log.error("Usuario ID {} no encontrado", id); return ResponseEntity.notFound().build(); });
    }

    @PutMapping("/usuarios/{id}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        log.info("PUT /api/auth/usuarios/{}/desactivar", id);
        return repository.findById(id).map(u -> {
            u.setActivo(false);
            log.info("Usuario {} desactivado", id);
            return ResponseEntity.ok(repository.save(u));
        }).orElseGet(() -> { log.error("Usuario ID {} no encontrado para desactivar", id); return ResponseEntity.notFound().build(); });
    }
}
