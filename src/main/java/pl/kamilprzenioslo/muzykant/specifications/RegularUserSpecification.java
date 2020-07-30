package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import pl.kamilprzenioslo.muzykant.persistance.entities.RegularUserEntity;

@Or({
  @Spec(path = "person.firstName", params = "name", spec = LikeIgnoreCase.class),
  @Spec(path = "person.lastName", params = "name", spec = LikeIgnoreCase.class),
  @Spec(path = "person.pseudo", params = "name", spec = LikeIgnoreCase.class)
})
public interface RegularUserSpecification extends UserSpecification<RegularUserEntity> {}
