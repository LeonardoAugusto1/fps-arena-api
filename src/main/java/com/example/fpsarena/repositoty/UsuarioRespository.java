package com.example.fpsarena.repositoty;

import com.example.fpsarena.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRespository extends JpaRepository<Usuario, Long> {
}
