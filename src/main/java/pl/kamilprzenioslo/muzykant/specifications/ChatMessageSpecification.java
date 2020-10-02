package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LessThan;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.ChatMessageEntity;

@And({
  @Spec(path = "sender.linkName", params = "participantLinkName", spec = In.class),
  @Spec(path = "recipient.linkName", params = "participantLinkName", spec = In.class),
  @Spec(path = "id", params = "sentBeforeMessageId", spec = LessThan.class),
})
public interface ChatMessageSpecification extends Specification<ChatMessageEntity> {}
