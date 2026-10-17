package com.example.fpsarena.service;

import com.example.fpsarena.dto.CriarCampeonatoRequest;
import com.example.fpsarena.model.Campeonato;
import com.example.fpsarena.model.InscricaoCampeonato;
import com.example.fpsarena.model.StatusCampeonato;
import com.example.fpsarena.model.Usuario;
import com.example.fpsarena.repository.CampeonatoRepository;
import com.example.fpsarena.repository.InscricaoCampeonatoRepository;
import com.example.fpsarena.repository.UsuarioRespository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final InscricaoCampeonatoRepository inscricaoCampeonatoRepository;
    private final UsuarioRespository usuarioRespository;
    private final CarteiraService carteiraService;

    public CampeonatoService(
            CampeonatoRepository campeonatoRepository,
            InscricaoCampeonatoRepository inscricaoCampeonatoRepository,
            UsuarioRespository usuarioRespository,
            CarteiraService carteiraService) {

        this.campeonatoRepository = campeonatoRepository;
        this.inscricaoCampeonatoRepository = inscricaoCampeonatoRepository;
        this.usuarioRespository = usuarioRespository;
        this.carteiraService = carteiraService;
    }

    public List<Campeonato> listar() {
        return campeonatoRepository.findAll();
    }

    @Transactional
    public Campeonato criar(CriarCampeonatoRequest request) {
        Campeonato campeonato = new Campeonato();

        campeonato.setNome(request.getNome());
        campeonato.setModo(request.getModo());
        campeonato.setTipo(request.getTipo());
        campeonato.setPremiacao(request.getPremiacao());
        campeonato.setValorInscricao(request.getValorInscricao());
        campeonato.setVagas(request.getVagas());
        campeonato.setInscritos(0);
        campeonato.setStatus(StatusCampeonato.ABERTO);

        return campeonatoRepository.save(campeonato);
    }
    @Transactional
    public void inscrever(Long campeonatoId, Long usuarioId) {
        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Campeonato não encontrado"
                ));
        Usuario usuario = usuarioRespository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));
        if (campeonato.getStatus() != StatusCampeonato.ABERTO) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Campeonato não está aberto para inscrições"
            );
        }
        if (campeonato.getInscritos() >= campeonato.getVagas()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Campeonato está lotado"
            );
        }
        if (inscricaoCampeonatoRepository.existsByCampeonatoIdAndUsuarioId(campeonatoId, usuarioId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Usuário já está inscrito neste campeonato"
            );
        }
        carteiraService.debitar(
                usuario,
                campeonato.getValorInscricao(),
                "Inscrição no campeonato"
        );
        InscricaoCampeonato inscricao = new InscricaoCampeonato();

        inscricao.setCampeonato(campeonato);
        inscricao.setUsuario(usuario);
        inscricao.setDataInscricao(LocalDateTime.now());

        inscricaoCampeonatoRepository.save(inscricao);

        campeonato.setInscritos(campeonato.getInscritos() + 1);
        campeonatoRepository.save(campeonato);

    }
    @Transactional
    public void cancelarInscricao(Long campeonatoId, Long usuarioId) {

        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Campeonato não encontrado"
                ));
        if (campeonato.getStatus() != StatusCampeonato.ABERTO) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Campeonato não está aberto para cancelamento"
            );
        }
        InscricaoCampeonato inscricao = inscricaoCampeonatoRepository
                .findByCampeonatoIdAndUsuarioId(campeonatoId, usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não está inscrito neste campeonato"
                ));
        carteiraService.creditar(
                inscricao.getUsuario(),
                campeonato.getValorInscricao(),
                "Cancelamento da inscrição"
        );
        inscricaoCampeonatoRepository.delete(inscricao);

        campeonato.setInscritos(campeonato.getInscritos() - 1);
        campeonatoRepository.save(campeonato);
    }

    public void deletar(Long id) {
        campeonatoRepository.deleteById(id);
    }
    @Transactional
    public void finalizar(Long campeonatoId, Long vencedorId) {
        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Campeonato não encontrado"
                ));

        if (campeonato.getStatus() == StatusCampeonato.FINALIZADO) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Campeonato já está finalizado"
            );
        }

        Usuario vencedor = usuarioRespository.findById(vencedorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário vencedor não encontrado"
                ));

        // Credita a premiação na carteira do vencedor gerando extrato de ENTRADA
        carteiraService.creditar(
                vencedor,
                campeonato.getPremiacao(),
                "Premiação do campeonato " + campeonato.getNome()
        );

        campeonato.setStatus(StatusCampeonato.FINALIZADO);
        campeonatoRepository.save(campeonato);
    }
}