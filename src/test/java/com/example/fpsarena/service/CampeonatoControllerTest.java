package com.example.fpsarena.service;

import com.example.fpsarena.config.SecurityConfig;
import com.example.fpsarena.controller.CampeonatoController;
import com.example.fpsarena.security.JwtAuthFilter;
import com.example.fpsarena.security.JwtService;
import com.example.fpsarena.service.CampeonatoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CampeonatoController.class)
@Import({SecurityConfig.class, JwtAuthFilter.class})
class CampeonatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CampeonatoService campeonatoService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void listarCampeonatosSemTokenDeveDar200() throws Exception {
        mockMvc.perform(get("/campeonatos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void rotaProtegidaSemTokenDeveDar401() throws Exception {
        mockMvc.perform(get("/partidas"))
                .andExpect(status().isUnauthorized());
    }
}