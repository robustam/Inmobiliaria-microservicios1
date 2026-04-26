package com.inmobiliaria.usuarioservice.repository;

import com.inmobiliaria.usuarioservice.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}