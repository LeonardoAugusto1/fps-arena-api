package com.example.fpsarena.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CriarPartidaRequest {

    @NotNull
    private Long usuarioId;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Pattern(regexp = "5v5|2v2|1v1")
    private String modo;

    @NotBlank
    private String tipo;

    @NotBlank
    private String mapa;

    @NotBlank
    private String regiao;

    @Size(max = 50)
    private String senha;

    @NotNull
    @PositiveOrZero
    private BigDecimal valorInscricao;

    @NotNull
    @Min(2)
    @Max(128)
    private Integer vagas;

    @NotNull
    private Boolean publica;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

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

    public String getMapa() {
        return mapa;
    }

    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public BigDecimal getValorInscricao() {
        return valorInscricao;
    }

    public void setValorInscricao(BigDecimal valorInscricao) {
        this.valorInscricao = valorInscricao;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public Boolean getPublica() {
        return publica;
    }

    public void setPublica(Boolean publica) {
        this.publica = publica;
    }
}