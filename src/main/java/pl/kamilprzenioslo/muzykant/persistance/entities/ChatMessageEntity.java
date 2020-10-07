package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "chat_message")
public class ChatMessageEntity extends AbstractPersistable<Long> {

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_user_profile_id")
  private UserEntity sender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recipient_user_profile_id")
  private UserEntity recipient;

  @Column(name = "sent_at", insertable = false, updatable = false)
  private LocalDateTime sentAt;

  private boolean seen;
}
