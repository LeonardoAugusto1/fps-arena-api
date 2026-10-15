package com.example.fpsarena.dto;

import com.example.fpsarena.model.TimeJogo;
import jakarta.validation.constraints.NotNull;

public class EntrarPartidaRequest {

    @NotNull(message = "timeJogo é obrigatório")
    private TimeJogo timeJogo;

    public TimeJogo getTimeJogo() {
        return timeJogo;
    }

    public void setTimeJogo(TimeJogo timeJogo) {
        this.timeJogo = timeJogo;
    }
}