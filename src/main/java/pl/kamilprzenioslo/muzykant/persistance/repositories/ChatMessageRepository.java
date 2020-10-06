package pl.kamilprzenioslo.muzykant.persistance.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pl.kamilprzenioslo.muzykant.persistance.entities.ChatMessageEntity;

public interface ChatMessageRepository
    extends JpaRepository<ChatMessageEntity, Long>, JpaSpecificationExecutor<ChatMessageEntity> {

  @Query(value = """
  SELECT
      id, sender_user_profile_id, recipient_user_profile_id, content, sent_at
  FROM (
    SELECT cm.*,
    ROW_NUMBER() OVER (PARTITION BY least(cm.sender_user_profile_id, cm.recipient_user_profile_id), greatest(cm.sender_user_profile_id, cm.recipient_user_profile_id)
    ORDER BY sent_at DESC, id DESC) message_rank
    FROM chat_message cm
    WHERE cm.sender_user_profile_id = ?1 OR cm.recipient_user_profile_id = ?1
   ) um_with_rank
  WHERE message_rank = 1;
  """, nativeQuery = true)
  List<ChatMessageEntity> getLastMessageFromEachConversation(int userId);
}
