package com.example.fpsarena.controller;

import com.example.fpsarena.dto.ValorRequest;
import com.example.fpsarena.model.Transacao;
import com.example.fpsarena.service.CarteiraService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {

    private final CarteiraService carteiraService;

    public CarteiraController(CarteiraService carteiraService) {
        this.carteiraService = carteiraService;
    }
    @PostMapping("/{usuarioId}/depositar")
    public Transacao depositar(@PathVariable Long usuarioId, @Valid @RequestBody ValorRequest request){
        return carteiraService.depositar(usuarioId, request.getValor());
    }
    @PostMapping("/{usuarioId}/sacar")
    public Transacao sacar(@PathVariable Long usuarioId, @Valid @RequestBody ValorRequest request){
        return carteiraService.sacar(usuarioId, request.getValor());
    }
    @GetMapping("/{usuarioId}/transacoes")
    public List<Transacao> listar(@PathVariable Long usuarioId){
        return carteiraService.listar(usuarioId);
    }
}
