package com.example.fpsarena.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "partida_jogador",
        uniqueConstraints = @UniqueConstraint (columnNames ={"partida_id","usuario_id"})
)
public class PartidaJogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partida_id",nullable = false)
    private Partida partida;

    @ManyToOne
    @JoinColumn(name = "usuario_id",nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeJogo timeJogo;

    @Column(nullable = false)
    private boolean lider;

    @Column(nullable = false)
    private boolean pronto;

    @Column(nullable = false)
    private LocalDateTime dataEntrada;

    public PartidaJogador() {
        this.dataEntrada = LocalDateTime.now();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Partida getPartida() {
        return partida;
    }
    public void setPartida(Partida partida) {
        this.partida = partida;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public TimeJogo getTimeJogo() {
        return timeJogo;
    }
    public void setTimeJogo(TimeJogo timeJogo) {
        this.timeJogo = timeJogo;
    }
    public boolean isLider() {
        return lider;
    }
    public void setLider(boolean lider) {
        this.lider = lider;
    }
    public boolean isPronto() {
        return pronto;
    }
    public void setPronto(boolean pronto) {
        this.pronto = pronto;
    }
    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }
    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}
