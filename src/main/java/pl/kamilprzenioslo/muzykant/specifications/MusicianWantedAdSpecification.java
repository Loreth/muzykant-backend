package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianWantedAdEntity;

@And({
  @Spec(path = "preferredGender", spec = Equal.class),
  @Spec(path = "minAge", spec = GreaterThanOrEqual.class),
  @Spec(path = "maxAge", spec = LessThanOrEqual.class)
})
public interface MusicianWantedAdSpecification extends AdSpecification<MusicianWantedAdEntity> {}
