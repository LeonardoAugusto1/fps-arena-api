package com.example.fpsarena.controller;

import com.example.fpsarena.dto.AlterarEmailRequest;
import com.example.fpsarena.dto.AlterarSenhaRequest;
import com.example.fpsarena.dto.UsuarioResponse;
import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repository.UsuarioRespository;
import com.example.fpsarena.service.UsuarioService;
import jakarta.validation.Valid;
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
    private final UsuarioService usuarioService;

    public UsuarioController(
            UsuarioRespository usuarioRespository,
            PasswordEncoder passwordEncoder,
            UsuarioService usuarioService
    ) {
        this.usuarioRespository = usuarioRespository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
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

    @PutMapping("/eu/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(
            Authentication authentication,
            @Valid @RequestBody AlterarSenhaRequest request
    ) {
        Long usuarioId = (Long) authentication.getPrincipal();
        usuarioService.alterarSenha(usuarioId, request);
    }

    @PutMapping("/eu/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarEmail(
            Authentication authentication,
            @Valid @RequestBody AlterarEmailRequest request
    ) {
        Long usuarioId = (Long) authentication.getPrincipal();
        usuarioService.alterarEmail(usuarioId, request);
    }
}