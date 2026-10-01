package com.example.fpsarena.repositoty;

import com.example.fpsarena.model.PartidaJogador;
import com.example.fpsarena.model.TimeJogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PartidaJogadorRepository extends JpaRepository<PartidaJogador, Long> {

    List<PartidaJogador> findByPartidaId(Long partidaId);

    Long countBypartidaIdAndTimeJogo(Long partidaId, LocalDateTime timeJogo);

    boolean existsByPartidaIdAndUsuarioId(Long partidaId, Long usuarioId);

    Optional<PartidaJogador> findByPartidaIdAndUsuarioId(Long partidaId, Long usuarioId);
    List<PartidaJogador> findByPartidaIdOrderByDataEntradaAsc(Long partidaId);

    long countByPartidaIdAndTimeJogo(Long partidaId, TimeJogo timeJogo);
}
