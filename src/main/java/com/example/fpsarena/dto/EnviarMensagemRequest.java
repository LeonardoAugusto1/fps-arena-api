package com.example.fpsarena.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EnviarMensagemRequest {

    @NotNull(message = "usuarioId é obrigatório")
    private Long usuarioId;

    @NotBlank(message = "texto é obrigatório")
    @Size(max = 100, message = "texto deve ter no máximo 100 caracteres")
    private String texto;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}