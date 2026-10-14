package com.example.fpsarena.repository;

import com.example.fpsarena.model.MensagemChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemChatRepository extends JpaRepository<MensagemChat, Long> {

    List<MensagemChat> findByPartidaIdOrderByDataHoraAsc(Long partidaId);
}
