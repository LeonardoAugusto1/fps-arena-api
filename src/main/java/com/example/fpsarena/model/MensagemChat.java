package com.example.fpsarena.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagem_chat")
public class MensagemChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partida_id",nullable = false)
    private Partida partida;

    @ManyToOne
    @JoinColumn(name = "usuario_id",nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 200)
    private String texto;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    public MensagemChat() {
        this.dataHora = LocalDateTime.now();
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
    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}

