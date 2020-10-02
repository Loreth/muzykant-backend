package pl.kamilprzenioslo.muzykant.controllers;

import java.util.List;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.ChatMessage;
import pl.kamilprzenioslo.muzykant.dtos.Conversation;
import pl.kamilprzenioslo.muzykant.persistance.entities.ChatMessageEntity;
import pl.kamilprzenioslo.muzykant.service.ChatService;
import pl.kamilprzenioslo.muzykant.specifications.ChatMessageSpecification;

@RestController
@RequestMapping(RestMappings.CHAT_MESSAGES)
public class ChatMessageController
    extends SpecificationRestController<
        ChatMessage, ChatMessageEntity, Long, ChatMessageSpecification, ChatService> {

  private final ChatService chatService;

  public ChatMessageController(ChatService chatService) {
    super(chatService);
    this.chatService = chatService;
  }

  @MessageMapping("/chat")
  public void receiveMessage(@Payload ChatMessage message) {
    chatService.save(message);
  }

  @GetMapping(RestMappings.CONVERSATIONS)
  public List<Conversation> getUserConversations(@RequestParam int userId) {
    return chatService.getUserConversations(userId);
  }
}
