package com.example.fpsarena.controller;

import com.example.fpsarena.dto.CampeonatoResponse;
import com.example.fpsarena.dto.CriarCampeonatoRequest;
import com.example.fpsarena.service.CampeonatoService;
import jakarta.validation.Valid;
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

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        campeonatoService.deletar(id);
    }
}