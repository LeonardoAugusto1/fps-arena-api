package com.example.fpsarena.dto;

import jakarta.validation.constraints.*;

public class AlterarEmailRequest {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    @Email
    @Size(max = 100)
    private String novoEmail;

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovoEmail() {
        return novoEmail;
    }

    public void setNovoEmail(String novoEmail) {
        this.novoEmail = novoEmail;
    }
}