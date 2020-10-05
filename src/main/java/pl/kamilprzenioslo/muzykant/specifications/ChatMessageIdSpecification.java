package pl.kamilprzenioslo.muzykant.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.ChatMessageEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.ChatMessageEntity_;

@Data
public class ChatMessageIdSpecification implements Specification<ChatMessageEntity> {
  private final Integer sentBeforeMessageId;

  @Override
  public Predicate toPredicate(
      Root<ChatMessageEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (sentBeforeMessageId != null) {
      cq.where(cb.lessThan(root.get(ChatMessageEntity_.ID), sentBeforeMessageId));
    }

    return cq.getRestriction();
  }
}
