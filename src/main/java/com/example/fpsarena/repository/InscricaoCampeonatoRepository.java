package com.example.fpsarena.repository;

import com.example.fpsarena.model.InscricaoCampeonato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InscricaoCampeonatoRepository extends JpaRepository<InscricaoCampeonato, Long> {

    boolean existsByCampeonatoIdAndUsuarioId(Long campeonatoId, Long usuarioId);
    Optional<InscricaoCampeonato> findByCampeonatoIdAndUsuarioId(
            Long campeonatoId,
            Long usuarioId
    );
}