package com.example.fpsarena.dto;

import com.example.fpsarena.model.Usuario;

import java.math.BigDecimal;

public class UsuarioResponse {

    private Long id;
    private String nome;
    private BigDecimal saldo;

    public UsuarioResponse(Long id, String nome, BigDecimal saldo) {
        this.id = id;
        this.nome = nome;
        this.saldo = saldo;
    }

    public static UsuarioResponse de(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSaldo()
        );
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}