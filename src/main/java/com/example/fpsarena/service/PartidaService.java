package com.example.fpsarena.service;

import com.example.fpsarena.dto.CriarPartidaRequest;
import com.example.fpsarena.model.Partida;
import com.example.fpsarena.model.StatusPartida;
import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repositoty.PartidaRepository;
import com.example.fpsarena.repositoty.UsuarioRespository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final UsuarioRespository usuarioRespository;
    private final CarteiraService carteiraService;

    public PartidaService(PartidaRepository partidaRepository,
                          UsuarioRespository usuarioRespository,
                          CarteiraService carteiraService) {
        this.partidaRepository = partidaRepository;
        this.usuarioRespository = usuarioRespository;
        this.carteiraService = carteiraService;
    }

    @Transactional
    public Partida criar(CriarPartidaRequest request) {
        Usuario criador = usuarioRespository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (request.getValorInscricao().compareTo(BigDecimal.ZERO) > 0) {
            carteiraService.debitar(criador, request.getValorInscricao(), "Inscrição em partida");
        }

        Partida partida = new Partida();
        partida.setNome(request.getNome());
        partida.setModo(request.getModo());
        partida.setTipo(request.getTipo());
        partida.setMapa(request.getMapa());
        partida.setRegiao(request.getRegiao());
        partida.setSenha(request.getSenha());
        partida.setValorInscricao(request.getValorInscricao());
        partida.setVagas(request.getVagas());
        partida.setPublica(request.getPublica());
        partida.setCriador(criador);
        partida.setStatus(StatusPartida.AGUARDANDO);
        partida.setDataCriacao(LocalDateTime.now());
        return partidaRepository.save(partida);
    }

    public List<Partida> listar() {
        return partidaRepository.findAll();
    }

    public Partida buscar(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada"));
    }
}