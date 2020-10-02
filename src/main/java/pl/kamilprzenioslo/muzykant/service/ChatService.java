package pl.kamilprzenioslo.muzykant.service;

import java.util.List;
import pl.kamilprzenioslo.muzykant.dtos.ChatMessage;
import pl.kamilprzenioslo.muzykant.dtos.Conversation;
import pl.kamilprzenioslo.muzykant.persistance.entities.ChatMessageEntity;

public interface ChatService
    extends SpecificationService<ChatMessage, ChatMessageEntity, Long>,
        CrudService<ChatMessage, Long> {

  List<Conversation> getUserConversations(int userId);
}
