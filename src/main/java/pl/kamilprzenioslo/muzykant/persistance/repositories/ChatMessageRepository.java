package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.ChatMessageEntity;

public interface ChatMessageRepository
    extends JpaRepository<ChatMessageEntity, Long>, JpaSpecificationExecutor<ChatMessageEntity> {}
