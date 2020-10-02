package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kamilprzenioslo.muzykant.dtos.ChatMessage;
import pl.kamilprzenioslo.muzykant.persistance.entities.ChatMessageEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.UserRepository;

@Mapper
public abstract class ChatMessageMapper implements BaseMapper<ChatMessage, ChatMessageEntity> {
  @Autowired private UserRepository userRepository;

  @Mapping(target = "senderLinkName", source = "sender.linkName")
  @Mapping(target = "recipientLinkName", source = "recipient.linkName")
  @Override
  public abstract ChatMessage mapToDto(ChatMessageEntity entity);

  @Mapping(target = "sender", source = "senderLinkName", qualifiedByName = "linkName")
  @Mapping(target = "recipient", source = "recipientLinkName", qualifiedByName = "linkName")
  @Override
  public abstract ChatMessageEntity mapToEntity(ChatMessage dto);

  @Named("linkName")
  UserEntity linkNameToUserEntity(String linkName) {
    return userRepository.findByLinkName(linkName).orElseThrow();
  }
}
