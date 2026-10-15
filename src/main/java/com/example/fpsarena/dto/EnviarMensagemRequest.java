package com.example.fpsarena.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EnviarMensagemRequest {



    @NotBlank(message = "texto é obrigatório")
    @Size(max = 100, message = "texto deve ter no máximo 100 caracteres")
    private String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}