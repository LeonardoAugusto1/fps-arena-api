package com.example.fpsarena.service;

import com.example.fpsarena.model.TipoTransacao;
import com.example.fpsarena.model.Transacao;
import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repositoty.TransacaoRepository;
import com.example.fpsarena.repositoty.UsuarioRespository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarteiraService {

    private final UsuarioRespository usuarioRespository;
    private final TransacaoRepository transacaoRepository;

    public CarteiraService(UsuarioRespository usuarioRespository, TransacaoRepository transacaoRepository) {
        this.usuarioRespository = usuarioRespository;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public Transacao depositar(Long usuarioId, BigDecimal valor) {
        Usuario usuario = buscarUsuario(usuarioId);
        usuario.setSaldo(usuario.getSaldo().add(valor));
        usuarioRespository.save(usuario);
        return registrar(usuario, "Depósito", valor, TipoTransacao.ENTRADA);
    }

    @Transactional
    public Transacao sacar(Long usuarioId, BigDecimal valor) {
        Usuario usuario = buscarUsuario(usuarioId);
        return debitar(usuario, valor, "Saque");
    }

    @Transactional
    public Transacao debitar(Usuario usuario, BigDecimal valor, String descricao) {
        if (usuario.getSaldo().compareTo(valor) < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Saldo insuficiente");
        }
        usuario.setSaldo(usuario.getSaldo().subtract(valor));
        usuarioRespository.save(usuario);
        return registrar(usuario, descricao, valor, TipoTransacao.SAIDA);
    }

    public List<Transacao> listar(Long usuarioId) {
        buscarUsuario(usuarioId);
        return transacaoRepository.findByUsuarioIdOrderByDataHoraDesc(usuarioId);
    }

    private Usuario buscarUsuario(Long usuarioId) {
        return usuarioRespository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    private Transacao registrar(Usuario usuario, String descricao, BigDecimal valor, TipoTransacao tipo) {
        Transacao transacao = new Transacao();
        transacao.setUsuario(usuario);
        transacao.setDescricao(descricao);
        transacao.setValor(valor);
        transacao.setTipo(tipo);
        transacao.setDataHora(LocalDateTime.now());
        return transacaoRepository.save(transacao);
    }
}