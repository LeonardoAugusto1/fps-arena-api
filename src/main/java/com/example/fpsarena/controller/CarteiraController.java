package com.example.fpsarena.controller;

import com.example.fpsarena.dto.TransacaoResponse;
import com.example.fpsarena.dto.ValorRequest;
import com.example.fpsarena.service.CarteiraService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {

    private final CarteiraService carteiraService;

    public CarteiraController(CarteiraService carteiraService) {
        this.carteiraService = carteiraService;
    }

    @PostMapping("/depositar")
    public TransacaoResponse depositar(Authentication authentication, @Valid @RequestBody ValorRequest request) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return TransacaoResponse.de(carteiraService.depositar(usuarioId, request.getValor()));
    }

    @PostMapping("/sacar")
    public TransacaoResponse sacar(Authentication authentication, @Valid @RequestBody ValorRequest request) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return TransacaoResponse.de(carteiraService.sacar(usuarioId, request.getValor()));
    }

    @GetMapping("/transacoes")
    public List<TransacaoResponse> listar(Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return carteiraService.listar(usuarioId).stream()
                .map(TransacaoResponse::de)
                .toList();
    }
}