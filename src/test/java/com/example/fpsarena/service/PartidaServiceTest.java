package com.example.fpsarena.service;

import com.example.fpsarena.dto.EntrarPartidaRequest;
import com.example.fpsarena.model.*;
import com.example.fpsarena.repository.MensagemChatRepository;
import com.example.fpsarena.repository.PartidaJogadorRepository;
import com.example.fpsarena.repository.PartidaRepository;
import com.example.fpsarena.repository.UsuarioRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartidaServiceTest {

    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private UsuarioRespository usuarioRespository;

    @Mock
    private CarteiraService carteiraService;

    @Mock
    private PartidaJogadorRepository partidaJogadorRepository;

    @Mock
    private MensagemChatRepository mensagemChatRepository;

    @InjectMocks
    private PartidaService partidaService;

    @Test
    void entrarEmTimeLotadoDeveDar409() {
        Partida partida = new Partida();
        partida.setModo("1v1");
        partida.setStatus(StatusPartida.AGUARDANDO);
        partida.setValorInscricao(BigDecimal.ZERO);

        Usuario usuario = new Usuario();

        EntrarPartidaRequest request = new EntrarPartidaRequest();
        request.setTimeJogo(TimeJogo.CT);

        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(usuarioRespository.findById(2L)).thenReturn(Optional.of(usuario));
        when(partidaJogadorRepository.countByPartidaIdAndTimeJogo(1L, TimeJogo.CT)).thenReturn(1L);

        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.entrar(1L, 2L, request)
        );

        assertEquals(HttpStatus.CONFLICT, erro.getStatusCode());
        assertEquals("Time lotado", erro.getReason());
        verify(partidaJogadorRepository, never()).save(any());
        verify(carteiraService, never()).debitar(any(), any(), any());
    }
    @Test
    void entrarJaEstandoNaPartidaDeveDar409() {
        Partida partida = new Partida();
        partida.setModo("5v5");
        partida.setStatus(StatusPartida.AGUARDANDO);
        partida.setValorInscricao(BigDecimal.ZERO);

        Usuario usuario = new Usuario();
        ReflectionTestUtils.setField(usuario, "id", 2L);

        EntrarPartidaRequest request = new EntrarPartidaRequest();
        request.setTimeJogo(TimeJogo.CT);

        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(usuarioRespository.findById(2L)).thenReturn(Optional.of(usuario));
        when(partidaJogadorRepository.existsByPartidaIdAndUsuarioId(1L, 2L)).thenReturn(true);

        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.entrar(1L, 2L, request)
        );

        assertEquals(HttpStatus.CONFLICT, erro.getStatusCode());
        assertEquals("Jogador já está nessa partida", erro.getReason());
        verify(partidaJogadorRepository, never()).save(any());
        verify(carteiraService, never()).debitar(any(), any(), any());
    }
    @Test
    void liderSaindoDevePassarALiderancaParaQuemSobrou() {
        Partida partida = new Partida();
        partida.setValorInscricao(BigDecimal.ZERO);

        PartidaJogador fichaDoLider = new PartidaJogador();
        fichaDoLider.setLider(true);

        PartidaJogador fichaDoOutro = new PartidaJogador();
        fichaDoOutro.setLider(false);

        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(partidaJogadorRepository.findByPartidaIdAndUsuarioId(1L, 2L)).thenReturn(Optional.of(fichaDoLider));
        when(partidaJogadorRepository.findByPartidaIdOrderByDataEntradaAsc(1L)).thenReturn(List.of(fichaDoOutro));

        partidaService.sair(1L, 2L);

        assertTrue(fichaDoOutro.isLider());
        verify(partidaJogadorRepository).delete(fichaDoLider);
        verify(partidaJogadorRepository).save(fichaDoOutro);
        verify(partidaRepository, never()).save(any());
    }
}