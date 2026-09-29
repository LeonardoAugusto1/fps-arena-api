package com.example.fpsarena.controller;


import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repositoty.UsuarioRespository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRespository usuarioRespository;

    public UsuarioController(UsuarioRespository usuarioRespository) {
        this.usuarioRespository = usuarioRespository;
    }
    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario){
        return usuarioRespository.save(usuario);
    }
    @GetMapping
    public List<Usuario> listar(){
        return usuarioRespository.findAll();
    }
}
