package com.example.fpsarena.dto;

import com.example.fpsarena.model.Campeonato;
import com.example.fpsarena.model.StatusCampeonato;

import java.math.BigDecimal;

public class CampeonatoResponse {

    private final Long id;
    private final String nome;
    private final String modo;
    private final String tipo;
    private final BigDecimal premiacao;
    private final Integer vagas;
    private final Integer inscritos;
    private final StatusCampeonato status;

    public CampeonatoResponse(Long id, String nome, String modo, String tipo,BigDecimal premiacao, Integer vagas, Integer inscritos, StatusCampeonato status) {
        this.id = id;
        this.nome = nome;
        this.modo = modo;
        this.tipo = tipo;
        this.premiacao = premiacao;
        this.vagas = vagas;
        this.inscritos = inscritos;
        this.status = status;
    }
    public static CampeonatoResponse de(Campeonato campeonato) {
        return  new CampeonatoResponse(
                campeonato.getId(),
                campeonato.getNome(),
                campeonato.getModo(),
                campeonato.getTipo(),
                campeonato.getPremiacao(),
                campeonato.getVagas(),
                campeonato.getInscritos(),
                campeonato.getStatus()
        );
    }
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getModo() {
        return modo;
    }
    public String getTipo() {
        return tipo;
    }
    public BigDecimal getPremiacao() {
        return premiacao;
    }
    public Integer getVagas() {
        return vagas;
    }
    public Integer getInscritos() {
        return inscritos;
    }
    public StatusCampeonato getStatus() {
        return status;
    }
}
