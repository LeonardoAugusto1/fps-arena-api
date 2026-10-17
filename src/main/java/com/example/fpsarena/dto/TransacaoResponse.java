package com.example.fpsarena.dto;

import com.example.fpsarena.model.TipoTransacao;
import com.example.fpsarena.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoResponse {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private TipoTransacao tipo;
    private LocalDateTime dataHora;

    public TransacaoResponse(Long id, String descricao, BigDecimal valor,
                             TipoTransacao tipo, LocalDateTime dataHora) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.dataHora = dataHora;
    }

    public static TransacaoResponse de(Transacao transacao) {
        return new TransacaoResponse(
                transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getTipo(),
                transacao.getDataHora()
        );
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}