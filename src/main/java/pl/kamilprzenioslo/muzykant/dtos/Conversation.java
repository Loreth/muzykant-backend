package pl.kamilprzenioslo.muzykant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Conversation {
  private String firstParticipantLinkName;
  private String firstParticipantProfileImageLink;
  private String secondParticipantLinkName;
  private String secondParticipantProfileImageLink;
  private ChatMessage lastMessage;
}
