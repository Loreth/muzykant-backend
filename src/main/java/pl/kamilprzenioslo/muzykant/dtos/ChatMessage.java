package pl.kamilprzenioslo.muzykant.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends BaseDto<Long> {
  private String content;
  private String senderLinkName;
  private String recipientLinkName;
  private LocalDateTime sentAt;
  private boolean seen;
}
