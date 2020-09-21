package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianEntity;

@Or({
  @Spec(path = "person.firstName", params = "name", spec = LikeIgnoreCase.class),
  @Spec(path = "person.lastName", params = "name", spec = LikeIgnoreCase.class),
  @Spec(path = "person.pseudo", params = "name", spec = LikeIgnoreCase.class)
})
@And({
  @Spec(path = "person.gender", params = "gender", spec = In.class),
  @Spec(
      path = "person.birthdate",
      params = {"birthdateAfterInclusive", "birthdateBeforeInclusive"},
      spec = Between.class)
})
public interface MusicianSpecification extends UserSpecification<MusicianEntity> {}
