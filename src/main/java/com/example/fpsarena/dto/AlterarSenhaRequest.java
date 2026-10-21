package com.example.fpsarena.dto;

import jakarta.validation.constraints.*;

public class AlterarSenhaRequest {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    @Size(min = 6, max = 72)
    private String novaSenha;

    @NotBlank
    private String confirmacao;

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(String confirmacao) {
        this.confirmacao = confirmacao;
    }
}