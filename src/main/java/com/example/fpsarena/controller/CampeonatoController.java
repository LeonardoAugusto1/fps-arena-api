package com.example.fpsarena.controller;

import com.example.fpsarena.dto.CampeonatoResponse;
import com.example.fpsarena.dto.CriarCampeonatoRequest;
import com.example.fpsarena.dto.InscricaoCampeonatoRequest;
import com.example.fpsarena.service.CampeonatoService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campeonatos")
public class CampeonatoController {

    private final CampeonatoService campeonatoService;

    public CampeonatoController(CampeonatoService campeonatoService) {
        this.campeonatoService = campeonatoService;
    }

    @GetMapping
    public List<CampeonatoResponse> listar() {
        return campeonatoService.listar()
                .stream()
                .map(CampeonatoResponse::de)
                .toList();
    }

    @PostMapping
    public CampeonatoResponse criar(@Valid @RequestBody CriarCampeonatoRequest request) {
        return CampeonatoResponse.de(campeonatoService.criar(request));
    }

    @PostMapping("/{id}/inscricoes")
    public void inscrever(
            @PathVariable Long id,
            Authentication authentication) {

        Long usuarioId = (Long) authentication.getPrincipal();
        campeonatoService.inscrever(id, usuarioId);
    }

    // PASSO 9: Cancelar Inscrição
    @DeleteMapping("/{id}/inscricoes/eu")
    public void cancelarInscricao(
            @PathVariable Long id,
            Authentication authentication) {

        Long usuarioId = (Long) authentication.getPrincipal();
        campeonatoService.cancelarInscricao(id, usuarioId);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        campeonatoService.deletar(id);
    }
    @PostMapping("/{id}/finalizar")
    public void finalizar(
            @PathVariable Long id,
            @RequestParam Long vencedorId) {

        campeonatoService.finalizar(id, vencedorId);
    }

}