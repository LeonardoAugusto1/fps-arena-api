package com.example.fpsarena.repository;

import com.example.fpsarena.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida,Long> {
    List<Partida> findByPublicaTrue();
}
