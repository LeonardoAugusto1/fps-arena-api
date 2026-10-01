package com.example.fpsarena.service;

import com.example.fpsarena.dto.CriarPartidaRequest;
import com.example.fpsarena.dto.EntrarPartidaRequest;
import com.example.fpsarena.dto.ProntoRequest;
import com.example.fpsarena.model.Partida;
import com.example.fpsarena.model.StatusPartida;
import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repositoty.PartidaRepository;
import com.example.fpsarena.repositoty.UsuarioRespository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.fpsarena.model.PartidaJogador;
import com.example.fpsarena.model.TimeJogo;
import com.example.fpsarena.repositoty.PartidaJogadorRepository;
import com.example.fpsarena.dto.EnviarMensagemRequest;
import com.example.fpsarena.model.MensagemChat;
import com.example.fpsarena.repositoty.MensagemChatRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final UsuarioRespository usuarioRespository;
    private final CarteiraService carteiraService;
    private final PartidaJogadorRepository partidaJogadorRepository;
    private final MensagemChatRepository mensagemChatRepository;

    // DEPOIS
    public PartidaService(PartidaRepository partidaRepository,
                          UsuarioRespository usuarioRespository,
                          CarteiraService carteiraService,
                          PartidaJogadorRepository partidaJogadorRepository,
                          MensagemChatRepository mensagemChatRepository) {
        this.partidaRepository = partidaRepository;
        this.usuarioRespository = usuarioRespository;
        this.carteiraService = carteiraService;
        this.partidaJogadorRepository = partidaJogadorRepository;
        this.mensagemChatRepository = mensagemChatRepository;
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
        Partida partidaSalva = partidaRepository.save(partida);

        PartidaJogador ficha = new PartidaJogador();
        ficha.setPartida(partidaSalva);
        ficha.setUsuario(criador);
        ficha.setTimeJogo(TimeJogo.CT);
        ficha.setLider(true);
        ficha.setPronto(false);
        partidaJogadorRepository.save(ficha);

        return partidaSalva;
    }@Transactional
        public PartidaJogador entrar(Long partidaId, EntrarPartidaRequest request) {
            Partida partida = buscar(partidaId);

            if (partida.getStatus() != StatusPartida.AGUARDANDO) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Partida não está aguardando jogadores");
            }
        Usuario usuario = usuarioRespository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (partidaJogadorRepository.existsByPartidaIdAndUsuarioId(partidaId, usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Jogador já está nessa partida");
        }

        int limiteDoTime = Integer.parseInt(partida.getModo().split("v")[0]);
        long ocupados = partidaJogadorRepository.countByPartidaIdAndTimeJogo(partidaId, request.getTimeJogo());
        if (ocupados >= limiteDoTime) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Time lotado");
        }

        if (partida.getValorInscricao().compareTo(BigDecimal.ZERO) > 0) {
            carteiraService.debitar(usuario, partida.getValorInscricao(), "Inscrição em partida");
        }

        PartidaJogador ficha = new PartidaJogador();
        ficha.setPartida(partida);
        ficha.setUsuario(usuario);
        ficha.setTimeJogo(request.getTimeJogo());
        ficha.setLider(false);
        ficha.setPronto(false);
        return partidaJogadorRepository.save(ficha);
    }

    public List<PartidaJogador> lobby(Long partidaId) {
        buscar(partidaId);
        return partidaJogadorRepository.findByPartidaId(partidaId);
    }
    @Transactional
    public PartidaJogador alternarPronto(Long partidaId, ProntoRequest request) {
        buscar(partidaId);

        PartidaJogador ficha = partidaJogadorRepository
                .findByPartidaIdAndUsuarioId(partidaId, request.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador não está nessa partida"));

        ficha.setPronto(!ficha.isPronto());
        return partidaJogadorRepository.save(ficha);
    }
    @Transactional
    public List<PartidaJogador> sair(Long partidaId, Long usuarioId) {
        Partida partida = buscar(partidaId);

        PartidaJogador ficha = partidaJogadorRepository
                .findByPartidaIdAndUsuarioId(partidaId, usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador não está nessa partida"));

        if (partida.getValorInscricao().compareTo(BigDecimal.ZERO) > 0) {
            carteiraService.creditar(ficha.getUsuario(), partida.getValorInscricao(), "Devolução de inscrição");
        }

        boolean eraLider = ficha.isLider();
        partidaJogadorRepository.delete(ficha);

        List<PartidaJogador> restantes = partidaJogadorRepository.findByPartidaIdOrderByDataEntradaAsc(partidaId);

        if (restantes.isEmpty()) {
            partida.setStatus(StatusPartida.FINALIZADA);
            partidaRepository.save(partida);
        } else if (eraLider) {
            PartidaJogador novoLider = restantes.get(0);
            novoLider.setLider(true);
            partidaJogadorRepository.save(novoLider);
        }

        return restantes;
    }
    public List<Partida> listar() {
        return partidaRepository.findAll();
    }
    @Transactional
    public MensagemChat enviarMensagem(Long partidaId, EnviarMensagemRequest request) {
        Partida partida = buscar(partidaId);

        PartidaJogador ficha = partidaJogadorRepository
                .findByPartidaIdAndUsuarioId(partidaId, request.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador não está nessa partida"));

        MensagemChat mensagem = new MensagemChat();
        mensagem.setPartida(partida);
        mensagem.setUsuario(ficha.getUsuario());
        mensagem.setTexto(request.getTexto().trim());
        return mensagemChatRepository.save(mensagem);
    }

    public Partida buscar(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada"));
    }
}