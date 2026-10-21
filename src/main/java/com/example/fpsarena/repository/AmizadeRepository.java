package com.example.fpsarena.repository;

import com.example.fpsarena.model.Amizade;
import com.example.fpsarena.model.AmizadeStatus;
import com.example.fpsarena.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AmizadeRepository extends JpaRepository<Amizade, Long> {

    Optional<Amizade> findByUsuarioSolicitanteAndUsuarioDestino(
            Usuario usuarioSolicitante,
            Usuario usuarioDestino
    );

    List<Amizade> findByUsuarioDestinoAndStatus(
            Usuario usuarioDestino,
            AmizadeStatus status
    );

    List<Amizade> findByUsuarioSolicitanteAndStatus(
            Usuario usuarioSolicitante,
            AmizadeStatus status
    );

    List<Amizade> findByUsuarioSolicitanteOrUsuarioDestino(
            Usuario usuarioSolicitante,
            Usuario usuarioDestino
    );
    @Query("""
    SELECT a
    FROM Amizade a
    JOIN FETCH a.usuarioSolicitante
    JOIN FETCH a.usuarioDestino
    WHERE a.id = :id
""")
    Optional<Amizade> buscarPorIdComUsuarios(@Param("id") Long id);
    @Query("""
    SELECT a
    FROM Amizade a
    JOIN FETCH a.usuarioSolicitante
    JOIN FETCH a.usuarioDestino
    WHERE a.usuarioSolicitante = :usuario
       OR a.usuarioDestino = :usuario
""")
    List<Amizade> buscarAmizadesComUsuarios(@Param("usuario") Usuario usuario);
}
