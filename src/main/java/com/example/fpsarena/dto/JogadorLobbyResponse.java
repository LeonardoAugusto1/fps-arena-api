package com.example.fpsarena.dto;

import com.example.fpsarena.model.PartidaJogador;
import com.example.fpsarena.model.TimeJogo;

import java.time.LocalDateTime;

public class JogadorLobbyResponse {

    private final Long usuarioId;
    private final String nome;
    private final TimeJogo timeJogo;

    private final boolean lider;

    private final boolean pronto;

    private final LocalDateTime dataEntrada;

    public JogadorLobbyResponse(Long usuarioId, String nome, TimeJogo timeJogo, boolean lider, boolean pronto, LocalDateTime dataEntrada) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.timeJogo = timeJogo;
        this.lider = lider;
        this.pronto = pronto;
        this.dataEntrada = dataEntrada;
    }
    public static JogadorLobbyResponse de(PartidaJogador ficha) {
        return new JogadorLobbyResponse(
                ficha.getUsuario().getId(),
                ficha.getUsuario().getNome(),
                ficha.getTimeJogo(),
                ficha.isLider(),
                ficha.isPronto(),
                ficha.getDataEntrada()
        );
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public TimeJogo getTimeJogo() {
        return timeJogo;
    }

    public boolean isLider() {
        return lider;
    }

    public boolean isPronto() {
        return pronto;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }
}


