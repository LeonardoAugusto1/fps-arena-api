package com.example.fpsarena.controller;

import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repositoty.UsuarioRespository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRespository usuarioRespository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(
            UsuarioRespository usuarioRespository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRespository = usuarioRespository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        return usuarioRespository.save(usuario);
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRespository.findAll();
    }
}