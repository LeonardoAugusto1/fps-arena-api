package com.example.fpsarena.service;

import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repository.TransacaoRepository;
import com.example.fpsarena.repository.UsuarioRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarteiraServiceTest {

    @Mock
    private UsuarioRespository usuarioRespository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private CarteiraService carteiraService;

    @Test
    void sacarComSaldoInsuficienteDeveDar422ENaoMexerNoSaldo() {
        Usuario usuario = new Usuario();
        usuario.setSaldo(new BigDecimal("50"));
        when(usuarioRespository.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> carteiraService.sacar(1L, new BigDecimal("80"))
        );

        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, erro.getStatusCode());
        assertEquals(0, new BigDecimal("50").compareTo(usuario.getSaldo()));
        verify(usuarioRespository, never()).save(any());
        verify(transacaoRepository, never()).save(any());
    }
}