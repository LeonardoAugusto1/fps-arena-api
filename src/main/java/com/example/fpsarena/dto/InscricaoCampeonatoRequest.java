package com.example.fpsarena.dto;

import jakarta.validation.constraints.NotNull;

public class InscricaoCampeonatoRequest {

    @NotNull
    private Long usuarioId;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}