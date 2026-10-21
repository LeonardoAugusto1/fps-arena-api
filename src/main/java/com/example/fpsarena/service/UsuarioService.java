package com.example.fpsarena.service;

import com.example.fpsarena.dto.AlterarEmailRequest;
import com.example.fpsarena.dto.AlterarSenhaRequest;
import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repository.UsuarioRespository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    private final UsuarioRespository usuarioRespository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRespository usuarioRespository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRespository = usuarioRespository;
        this.passwordEncoder = passwordEncoder;
    }

    public void alterarSenha(Long usuarioId, AlterarSenhaRequest request) {

        Usuario usuario = usuarioRespository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (!request.getNovaSenha().equals(request.getConfirmacao())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "A confirmação não é igual à nova senha");
        }

        if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Senha atual incorreta");
        }

        usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
        usuarioRespository.save(usuario);
    }

    public void alterarEmail(Long usuarioId, AlterarEmailRequest request) {

        Usuario usuario = usuarioRespository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Senha atual incorreta");
        }

        String novoEmail = request.getNovoEmail().trim().toLowerCase();

        if (novoEmail.equals(usuario.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Este já é o seu e-mail atual");
        }

        if (usuarioRespository.existsByEmail(novoEmail)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Este e-mail já está em uso");
        }

        usuario.setEmail(novoEmail);
        usuarioRespository.save(usuario);
    }
}