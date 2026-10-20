package com.example.fpsarena.dto;

import com.example.fpsarena.model.TimeJogo;
import jakarta.validation.constraints.Size;

public class EntrarPartidaRequest {

    private TimeJogo timeJogo;

    @Size(max = 50)
    private String senha;

    public TimeJogo getTimeJogo() {
        return timeJogo;
    }

    public void setTimeJogo(TimeJogo timeJogo) {
        this.timeJogo = timeJogo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}