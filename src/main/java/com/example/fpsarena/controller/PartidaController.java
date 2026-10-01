package com.example.fpsarena.controller;

import com.example.fpsarena.dto.*;
import com.example.fpsarena.model.Partida;
import com.example.fpsarena.model.PartidaJogador;
import com.example.fpsarena.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.example.fpsarena.model.MensagemChat;
import java.util.List;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private final PartidaService partidaService;
    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }
    @PostMapping
    public PartidaResponse criar(@Valid @RequestBody CriarPartidaRequest request) {
        return PartidaResponse.de(partidaService.criar(request));
    }
    @GetMapping
    public List<PartidaResponse> listar() {
        return partidaService.listar().stream().map(PartidaResponse::de).toList();
    }
    @GetMapping("/{id}")
    public PartidaResponse buscar(@PathVariable Long id) {
        return PartidaResponse.de(partidaService.buscar(id));
    }
    @GetMapping("/{id}/lobby")
    public List<JogadorLobbyResponse> lobby(@PathVariable Long id) {
        return partidaService.lobby(id).stream().map(JogadorLobbyResponse::de).toList();
    }
    @PostMapping("/{id}/pronto")
    public JogadorLobbyResponse pronto(@PathVariable Long id, @Valid @RequestBody ProntoRequest request) {
        return JogadorLobbyResponse.de(partidaService.alternarPronto(id, request));
    }

    @DeleteMapping("/{id}/jogadores/{usuarioId}")
    public List<JogadorLobbyResponse> sair(@PathVariable Long id, @PathVariable Long usuarioId) {
        return partidaService.sair(id, usuarioId).stream().map(JogadorLobbyResponse::de).toList();
    }
    @PostMapping("/{id}/mensagens")
    public MensagemChatResponse enviarMensagem(
            @PathVariable Long id,
            @Valid @RequestBody EnviarMensagemRequest request) {

        return MensagemChatResponse.de(
                partidaService.enviarMensagem(id, request)
        );
    }
}