package com.example.fpsarena.service;

import com.example.fpsarena.dto.CriarCampeonatoRequest;
import com.example.fpsarena.model.Campeonato;
import com.example.fpsarena.model.StatusCampeonato;
import com.example.fpsarena.repositoty.CampeonatoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampeonatoService {

    private CampeonatoRepository campeonatoRepository;

    public CampeonatoService(CampeonatoRepository campeonatoRepository) {
        this.campeonatoRepository = campeonatoRepository;
    }

    public List<Campeonato> listar() {
        return campeonatoRepository.findAll();
    }

    @Transactional
    public Campeonato criar(CriarCampeonatoRequest request) {
        Campeonato campeonato = new Campeonato();

        campeonato.setNome(request.getNome());
        campeonato.setModo(request.getModo());
        campeonato.setTipo(request.getTipo());
        campeonato.setPremiacao(request.getPremiacao());
        campeonato.setVagas(request.getVagas());
        campeonato.setInscritos(0);
        campeonato.setStatus(StatusCampeonato.ABERTO);

        return campeonatoRepository.save(campeonato);
    }

    public void deletar(Long id) {
        campeonatoRepository.deleteById(id);
    }
}