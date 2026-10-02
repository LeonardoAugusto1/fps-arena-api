package com.example.fpsarena.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CriarCampeonatoRequest {

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Pattern(regexp = "5v5|2v2|1v1")
    private String modo;

    @NotBlank
    @Size(max = 50)
    private String tipo;

    @NotNull
    @PositiveOrZero
    private BigDecimal premiacao;

    @NotNull
    @Min(2)
    @Max(128)
    private Integer vagas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPremiacao() {
        return premiacao;
    }

    public void setPremiacao(BigDecimal premiacao) {
        this.premiacao = premiacao;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }
}