package com.example.fpsarena.controller;

import com.example.fpsarena.dto.LoginRequest;
import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repositoty.UsuarioRespository;
import com.example.fpsarena.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {

    private final UsuarioRespository usuarioRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UsuarioRespository usuarioRespository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRespository = usuarioRespository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioRespository
                .findByNome(request.getNome())
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("erro", "Usuário ou senha inválidos"));
        }

        if (!passwordEncoder.matches(
                request.getSenha(),
                usuario.getSenha()
        )) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("erro", "Usuário ou senha inválidos"));
        }

        String token = jwtService.gerarToken(
                usuario.getId(),
                usuario.getNome(),
                usuario.getRole().name()
        );

        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }
}