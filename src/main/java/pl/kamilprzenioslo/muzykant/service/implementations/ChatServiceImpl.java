package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.ChatMessage;
import pl.kamilprzenioslo.muzykant.persistance.entities.ChatMessageEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.ChatMessageRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.CredentialsRepository;
import pl.kamilprzenioslo.muzykant.service.ChatService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class ChatServiceImpl
    extends BaseSpecificationCrudService<
        ChatMessage, ChatMessageEntity, Long, ChatMessageRepository>
    implements ChatService {
  private final SimpMessagingTemplate messagingTemplate;
  private final CredentialsRepository credentialsRepository;

  public ChatServiceImpl(
      ChatMessageRepository repository,
      BaseMapper<ChatMessage, ChatMessageEntity> mapper,
      SimpMessagingTemplate messagingTemplate,
      CredentialsRepository credentialsRepository) {
    super(repository, mapper);
    this.messagingTemplate = messagingTemplate;
    this.credentialsRepository = credentialsRepository;
  }

  @Override
  public ChatMessage save(ChatMessage message) {
    String recipientUsername =
        credentialsRepository
            .findByUser_LinkName(message.getRecipientLinkName())
            .orElseThrow()
            .getEmail();
    ChatMessage savedMessage = super.save(message);
    messagingTemplate.convertAndSendToUser(recipientUsername, "/queue/chat", savedMessage);
    return savedMessage;
  }
}
