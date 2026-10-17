package com.example.fpsarena.controller;

import com.example.fpsarena.dto.UsuarioResponse;
import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repository.UsuarioRespository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public UsuarioResponse criar(@RequestBody Usuario usuario) {

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        return UsuarioResponse.de(usuarioRespository.save(usuario));
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioRespository.findAll().stream()
                .map(UsuarioResponse::de)
                .toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioRespository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return UsuarioResponse.de(usuario);
    }

    @GetMapping("/eu")
    public UsuarioResponse eu(Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        Usuario usuario = usuarioRespository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return UsuarioResponse.de(usuario);
    }
}