package com.example.fpsarena.controller;

import com.example.fpsarena.dto.CriarPartidaRequest;
import com.example.fpsarena.model.Partida;
import com.example.fpsarena.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private final PartidaService partidaService;
    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }
    @PostMapping
    public Partida criar(@Valid @RequestBody CriarPartidaRequest request) {
        return  partidaService.criar(request);
    }
    @GetMapping
    public List<Partida> listar() {
        return partidaService.listar();
    }
    @GetMapping("/{id}")
    public Partida buscar(@PathVariable Long id) {
        return partidaService.buscar(id);
    }
}
