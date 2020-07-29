package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;

@And({
  @Spec(path = "city", spec = Like.class),
  @Spec(path = "voivodeship", spec = Like.class)
})
public interface UserSpecification<T extends UserEntity> extends Specification<T> {}
