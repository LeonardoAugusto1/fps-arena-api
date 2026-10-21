package com.example.fpsarena.controller;

import com.example.fpsarena.dto.AmizadeResponse;
import com.example.fpsarena.service.AmizadeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amizades")
public class AmizadeController {

    private final AmizadeService amizadeService;

    public AmizadeController(AmizadeService amizadeService) {
        this.amizadeService = amizadeService;
    }

    @PostMapping("/{usuarioDestinoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AmizadeResponse enviarSolicitacao(
            Authentication authentication,
            @PathVariable Long usuarioDestinoId
    ) {
        Long usuarioId = (Long) authentication.getPrincipal();

        return AmizadeResponse.de(
                amizadeService.enviarSolicitacao(
                        usuarioId,
                        usuarioDestinoId
                )
        );
    }

    @PutMapping("/{amizadeId}/aceitar")
    public AmizadeResponse aceitarSolicitacao(
            Authentication authentication,
            @PathVariable Long amizadeId
    ) {
        Long usuarioId = (Long) authentication.getPrincipal();

        return AmizadeResponse.de(
                amizadeService.aceitarSolicitacao(
                        amizadeId,
                        usuarioId
                )
        );
    }
    @PutMapping("/{amizadeId}/recusar")
    public AmizadeResponse recusarSolicitacao(
            Authentication authentication,
            @PathVariable Long amizadeId
    ) {
        Long usuarioId = (Long) authentication.getPrincipal();

        return AmizadeResponse.de(
                amizadeService.recusarSolicitacao(
                        amizadeId,
                        usuarioId
                )
        );
    }

    @GetMapping
    public List<AmizadeResponse> listarAmizades(
            Authentication authentication
    ) {
        Long usuarioId = (Long) authentication.getPrincipal();

        return amizadeService.listarAmizades(usuarioId)
                .stream()
                .map(AmizadeResponse::de)
                .toList();
    }
}