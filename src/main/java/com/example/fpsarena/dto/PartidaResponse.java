package com.example.fpsarena.dto;

import com.example.fpsarena.model.Partida;
import com.example.fpsarena.model.StatusPartida;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PartidaResponse {

    private final Long id;
    private final String nome;
    private final String modo;
    private final String tipo;
    private final String mapa;
    private final String regiao;
    private final BigDecimal valorInscricao;
    private final Integer vagas;
    private final Boolean publica;
    private final StatusPartida status;
    private final LocalDateTime dataCriacao;
    private final Long criadorId;
    private final String criadorNome;

    public PartidaResponse(Long id, String nome, String modo, String tipo, String mapa, String regiao,
                           BigDecimal valorInscricao, Integer vagas, Boolean publica,
                           StatusPartida status, LocalDateTime dataCriacao,
                           Long criadorId, String criadorNome) {
        this.id = id;
        this.nome = nome;
        this.modo = modo;
        this.tipo = tipo;
        this.mapa = mapa;
        this.regiao = regiao;
        this.valorInscricao = valorInscricao;
        this.vagas = vagas;
        this.publica = publica;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.criadorId = criadorId;
        this.criadorNome = criadorNome;
    }

    public static PartidaResponse de(Partida partida) {
        return new PartidaResponse(
                partida.getId(),
                partida.getNome(),
                partida.getModo(),
                partida.getTipo(),
                partida.getMapa(),
                partida.getRegiao(),
                partida.getValorInscricao(),
                partida.getVagas(),
                partida.getPublica(),
                partida.getStatus(),
                partida.getDataCriacao(),
                partida.getCriador().getId(),
                partida.getCriador().getNome()
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

    public String getMapa() {
        return mapa;
    }

    public String getRegiao() {
        return regiao;
    }

    public BigDecimal getValorInscricao() {
        return valorInscricao;
    }

    public Integer getVagas() {
        return vagas;
    }

    public Boolean getPublica() {
        return publica;
    }

    public StatusPartida getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Long getCriadorId() {
        return criadorId;
    }

    public String getCriadorNome() {
        return criadorNome;
    }
}