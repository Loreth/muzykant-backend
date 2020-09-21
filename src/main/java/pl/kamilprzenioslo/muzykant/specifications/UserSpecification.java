package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;

@And({
  @Spec(path = "city", spec = LikeIgnoreCase.class),
  @Spec(path = "linkName", spec = Equal.class)
})
public interface UserSpecification<T extends UserEntity> extends Specification<T> {}
