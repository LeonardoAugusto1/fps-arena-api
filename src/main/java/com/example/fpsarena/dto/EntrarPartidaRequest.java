package com.example.fpsarena.dto;

import com.example.fpsarena.model.TimeJogo;
import jakarta.validation.constraints.NotNull;

public class EntrarPartidaRequest {
    @NotNull(message = "usuarioId é obrigatório")
    private Long usuarioId;

    @NotNull(message = "timeJogo é obrigatório")
    private TimeJogo timeJogo;

    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    public TimeJogo getTimeJogo() {
        return timeJogo;
    }
    public void setTimeJogo(TimeJogo timeJogo) {
        this.timeJogo = timeJogo;
    }
}