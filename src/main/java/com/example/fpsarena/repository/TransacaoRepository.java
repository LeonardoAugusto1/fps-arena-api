package com.example.fpsarena.repository;

import com.example.fpsarena.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByUsuarioIdOrderByDataHoraDesc(Long usuarioId);
}
