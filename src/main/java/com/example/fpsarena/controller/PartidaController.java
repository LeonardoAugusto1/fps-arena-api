package com.example.fpsarena.controller;

import com.example.fpsarena.dto.*;
import com.example.fpsarena.model.Partida;
import com.example.fpsarena.model.PartidaJogador;
import com.example.fpsarena.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.example.fpsarena.model.MensagemChat;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private final PartidaService partidaService;
    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }
    @PostMapping
    public PartidaResponse criar(@Valid @RequestBody CriarPartidaRequest request, Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return PartidaResponse.de(partidaService.criar(usuarioId, request));
    }
    @GetMapping
    public List<PartidaResponse> listar() {
        return partidaService.listar().stream().map(PartidaResponse::de).toList();
    }

    @GetMapping("/{id}")
    public PartidaResponse buscar(@PathVariable Long id) {
        return PartidaResponse.de(partidaService.buscar(id));
    }
    @GetMapping({"/{id}/lobby", "/{id}/jogadores"})
    public List<JogadorLobbyResponse> lobby(@PathVariable Long id) {
        return partidaService.lobby(id).stream().map(JogadorLobbyResponse::de).toList();
    }
    @PostMapping("/{id}/pronto")
    public JogadorLobbyResponse pronto(@PathVariable Long id, Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();

        ProntoRequest request = new ProntoRequest();
        request.setUsuarioId(usuarioId);

        return JogadorLobbyResponse.de(partidaService.alternarPronto(id, request));
    }
    @PostMapping("/{id}/iniciar")
    public PartidaResponse iniciar(@PathVariable Long id, Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return PartidaResponse.de(partidaService.iniciar(id, usuarioId));
    }

    @DeleteMapping("/{id}/jogadores/eu")
    public List<JogadorLobbyResponse> sair(@PathVariable Long id, Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return partidaService.sair(id, usuarioId).stream().map(JogadorLobbyResponse::de).toList();
    }
    @PostMapping("/{id}/mensagens")
    public MensagemChatResponse enviarMensagem(
            @PathVariable Long id,
            @Valid @RequestBody EnviarMensagemRequest request,
            Authentication authentication) {

        Long usuarioId = (Long) authentication.getPrincipal();

        return MensagemChatResponse.de(
                partidaService.enviarMensagem(id, usuarioId, request)
        );
    }
    @PostMapping("/{id}/entrar")
    public JogadorLobbyResponse entrar(@PathVariable Long id, @Valid @RequestBody EntrarPartidaRequest request, Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return JogadorLobbyResponse.de(partidaService.entrar(id, usuarioId, request));
    }
    @PostMapping("/{id}/time")
    public JogadorLobbyResponse escolherTime(
            @PathVariable Long id,
            @RequestBody EscolherTimeRequest request,
            Authentication authentication) {

        Long usuarioId = (Long) authentication.getPrincipal();

        return JogadorLobbyResponse.de(
                partidaService.escolherTime(
                        id,
                        usuarioId,
                        request.getTimeJogo()
                )
        );
    }

    @GetMapping("/{id}/mensagens")
    public List<MensagemChatResponse> listarMensagens(@PathVariable Long id) {
        return partidaService.listarMensagens(id).stream().map(MensagemChatResponse::de).toList();
    }
}