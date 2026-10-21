package com.example.fpsarena.service;

import com.example.fpsarena.model.Amizade;
import com.example.fpsarena.model.AmizadeStatus;
import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repository.AmizadeRepository;
import com.example.fpsarena.repository.UsuarioRespository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AmizadeService {

    private final AmizadeRepository amizadeRepository;
    private final UsuarioRespository usuarioRespository;

    public AmizadeService(
            AmizadeRepository amizadeRepository,
            UsuarioRespository usuarioRespository
    ) {
        this.amizadeRepository = amizadeRepository;
        this.usuarioRespository = usuarioRespository;
    }

    public Amizade enviarSolicitacao(Long usuarioId, Long usuarioDestinoId) {

        if (usuarioId.equals(usuarioDestinoId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Você não pode adicionar a si mesmo"
            );
        }

        Usuario usuario = usuarioRespository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        Usuario destino = usuarioRespository.findById(usuarioDestinoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário destino não encontrado"
                ));

        boolean relacionamentoExiste =
                amizadeRepository
                        .findByUsuarioSolicitanteAndUsuarioDestino(usuario, destino)
                        .isPresent()
                        ||
                        amizadeRepository
                                .findByUsuarioSolicitanteAndUsuarioDestino(destino, usuario)
                                .isPresent();

        if (relacionamentoExiste) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe uma solicitação ou amizade entre vocês"
            );
        }

        Amizade amizade = new Amizade();

        amizade.setUsuarioSolicitante(usuario);
        amizade.setUsuarioDestino(destino);
        amizade.setStatus(AmizadeStatus.PENDENTE);

        return amizadeRepository.save(amizade);
    }

    public Amizade aceitarSolicitacao(Long amizadeId, Long usuarioId) {

        Amizade amizade = amizadeRepository.buscarPorIdComUsuarios(amizadeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitação de amizade não encontrada"
                ));

        if (!amizade.getUsuarioDestino().getId().equals(usuarioId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não pode aceitar essa solicitação"
            );
        }

        if (amizade.getStatus() != AmizadeStatus.PENDENTE) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Essa solicitação não está pendente"
            );
        }

        amizade.setStatus(AmizadeStatus.ACEITA);

        return amizadeRepository.save(amizade);
    }
    public List<Amizade> listarAmizades(Long usuarioId) {

        Usuario usuario = usuarioRespository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        return amizadeRepository.buscarAmizadesComUsuarios(usuario);
    }
    public Amizade recusarSolicitacao(Long amizadeId, Long usuarioId) {

        Amizade amizade = amizadeRepository.buscarPorIdComUsuarios(amizadeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitação de amizade não encontrada"
                ));

        if (!amizade.getUsuarioDestino().getId().equals(usuarioId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não pode recusar essa solicitação"
            );
        }

        if (amizade.getStatus() != AmizadeStatus.PENDENTE) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Essa solicitação não está pendente"
            );
        }

        amizade.setStatus(AmizadeStatus.RECUSADA);

        return amizadeRepository.save(amizade);
    }
}
