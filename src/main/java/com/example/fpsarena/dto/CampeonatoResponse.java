package com.example.fpsarena.dto;

import com.example.fpsarena.model.Campeonato;
import com.example.fpsarena.model.StatusCampeonato;

import java.math.BigDecimal;

public record CampeonatoResponse(Long id, String nome, String modo, String tipo, BigDecimal premiacao,
                                 BigDecimal valorInscricao, Integer vagas, Integer inscritos, StatusCampeonato status) {

    public static CampeonatoResponse de(Campeonato campeonato) {
        return new CampeonatoResponse(
                campeonato.getId(),
                campeonato.getNome(),
                campeonato.getModo(),
                campeonato.getTipo(),
                campeonato.getPremiacao(),
                campeonato.getValorInscricao(),
                campeonato.getVagas(),
                campeonato.getInscritos(),
                campeonato.getStatus()
        );
    }
}
