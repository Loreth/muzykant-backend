package pl.kamilprzenioslo.muzykant.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.controllers.mappings.RestMappings;
import pl.kamilprzenioslo.muzykant.controllers.mappings.WebsocketMappings;
import pl.kamilprzenioslo.muzykant.dtos.ChatMessage;
import pl.kamilprzenioslo.muzykant.dtos.Conversation;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.service.ChatService;
import pl.kamilprzenioslo.muzykant.specifications.ChatMessageIdSpecification;
import pl.kamilprzenioslo.muzykant.specifications.ChatMessageSpecification;

@RestController
@RequestMapping(RestMappings.CHAT_MESSAGES)
public class ChatMessageController extends BaseRestController<ChatMessage, Long> {

  private final ChatService chatService;

  public ChatMessageController(ChatService chatService) {
    super(chatService);
    this.chatService = chatService;
  }

  @MessageMapping(WebsocketMappings.CHAT)
  public void receiveMessage(@Payload ChatMessage message) {
    chatService.save(message);
  }

  @MessageMapping(WebsocketMappings.SEEN_CONVERSATION)
  public void markMessagesFromUserAsSeen(
      @Payload String userLinkName, Authentication authentication) {
    chatService.markMessagesFromUserAsSeen(
        (Credentials) authentication.getPrincipal(), userLinkName);
  }

  @GetMapping(RestMappings.CONVERSATIONS)
  public List<Conversation> getUserConversations(@RequestParam int userId) {
    return chatService.getUserConversations(userId);
  }

  @GetMapping(RestMappings.SEARCH)
  public Page<ChatMessage> getAllWithGivenParameters(
      ChatMessageSpecification specification,
      @RequestParam(required = false) Integer sentBeforeMessageId,
      Pageable pageable) {
    return chatService.findAll(
        Stream.of(specification, new ChatMessageIdSpecification(sentBeforeMessageId))
            .collect(Collectors.toList()),
        pageable);
  }
}
