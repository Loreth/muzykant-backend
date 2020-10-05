package pl.kamilprzenioslo.muzykant.service.implementations;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.ChatMessage;
import pl.kamilprzenioslo.muzykant.dtos.Conversation;
import pl.kamilprzenioslo.muzykant.persistance.entities.ChatMessageEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.ChatMessageRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.CredentialsRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.UserRepository;
import pl.kamilprzenioslo.muzykant.service.ChatService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class ChatServiceImpl
    extends BaseSpecificationCrudService<
        ChatMessage, ChatMessageEntity, Long, ChatMessageRepository>
    implements ChatService {
  private final SimpMessagingTemplate messagingTemplate;
  private final CredentialsRepository credentialsRepository;
  private final UserRepository userRepository;

  public ChatServiceImpl(
      ChatMessageRepository repository,
      BaseMapper<ChatMessage, ChatMessageEntity> mapper,
      SimpMessagingTemplate messagingTemplate,
      CredentialsRepository credentialsRepository,
      UserRepository userRepository) {
    super(repository, mapper);
    this.messagingTemplate = messagingTemplate;
    this.credentialsRepository = credentialsRepository;
    this.userRepository = userRepository;
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

  @Override
  public List<Conversation> getUserConversations(int userId) {
    UserEntity user = userRepository.findById(userId).orElseThrow();

    return repository.getLastMessageFromEachConversation(user.getId()).stream()
        .map(
            message -> {
              UserEntity secondUser = getSecondUserForMessage(user, message);
              return mapToConversation(user, secondUser, message);
            })
        .collect(Collectors.toList());
  }

  private UserEntity getSecondUserForMessage(UserEntity firstUser, ChatMessageEntity chatMessage) {
    return chatMessage.getSender().equals(firstUser)
        ? chatMessage.getRecipient()
        : chatMessage.getSender();
  }

  private Conversation mapToConversation(
      UserEntity user, UserEntity secondUser, ChatMessageEntity message) {
    return new Conversation(
        user.getLinkName(),
        user.getDisplayName(),
        user.getProfileImageLink(),
        secondUser.getLinkName(),
        secondUser.getDisplayName(),
        secondUser.getProfileImageLink(),
        mapper.mapToDto(message));
  }
}
