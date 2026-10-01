package com.example.fpsarena.dto;

import com.example.fpsarena.model.MensagemChat;

import java.time.LocalDateTime;

public class MensagemChatResponse {

    private final Long id;
    private final Long usuarioId;
    private final String nome;
    private final String texto;
    private final LocalDateTime dataHora;

    public MensagemChatResponse(Long id, Long usuarioId, String nome, String texto, LocalDateTime dataHora) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.texto = texto;
        this.dataHora = dataHora;
    }

    public static MensagemChatResponse de(MensagemChat mensagem) {
        return new MensagemChatResponse(
                mensagem.getId(),
                mensagem.getUsuario().getId(),
                mensagem.getUsuario().getNome(),
                mensagem.getTexto(),
                mensagem.getDataHora()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}