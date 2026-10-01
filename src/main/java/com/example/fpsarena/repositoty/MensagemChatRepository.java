package com.example.fpsarena.repositoty;

import com.example.fpsarena.model.MensagemChat;
import com.example.fpsarena.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemChatRepository extends JpaRepository<MensagemChat, Long> {

    List<MensagemChat> findByPartidaIdOrderByDataHoraAsc(Long partidaId);
}
