package com.example.fpsarena.dto;

import jakarta.validation.constraints.NotNull;

public class ProntoRequest {

    @NotNull(message = "usuarioId é obrigatório")
    private Long usuarioId;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}