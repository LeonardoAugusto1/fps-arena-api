package com.example.fpsarena.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "campeonato")
public class Campeonato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String modo;

    @Column(nullable = false, length = 100)
    private String tipo;

    @Column(nullable = false,precision = 12, scale = 2)
    private BigDecimal premiacao;

    @Column(nullable = false)
    private Integer vagas;

    @Column(nullable = false)
    private Integer inscritos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusCampeonato status;

public Campeonato() {
}
public Long getId() {
    return id;
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
public Integer getInscritos() {
    return inscritos;
}
public void setInscritos(Integer inscritos) {
    this.inscritos = inscritos;
}
public StatusCampeonato getStatus() {
    return status;
}
public void setStatus(StatusCampeonato status) {
    this.status = status;
}


}
