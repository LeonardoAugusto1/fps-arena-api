package com.example.fpsarena.dto;

import com.example.fpsarena.model.Amizade;
import com.example.fpsarena.model.AmizadeStatus;

public record AmizadeResponse(
        Long id,
        Long usuarioSolicitanteId,
        String usuarioSolicitanteNome,
        Long usuarioDestinoId,
        String usuarioDestinoNome,
        AmizadeStatus status
) {

    public static AmizadeResponse de(Amizade amizade) {

        return new AmizadeResponse(
                amizade.getId(),
                amizade.getUsuarioSolicitante().getId(),
                amizade.getUsuarioSolicitante().getNome(),
                amizade.getUsuarioDestino().getId(),
                amizade.getUsuarioDestino().getNome(),
                amizade.getStatus()
        );
    }
}