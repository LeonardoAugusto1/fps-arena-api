package com.example.fpsarena.repositoty;

import com.example.fpsarena.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRespository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNome(String nome);

    boolean existsByNome(String nome);
}