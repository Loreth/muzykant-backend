package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.Null;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianWantedAdEntity;

@Conjunction(
    value = {
      @Or({
        @Spec(
            path = "maxAge",
            params = "minAge",
            defaultVal = "0",
            spec = GreaterThanOrEqual.class),
        @Spec(path = "maxAge", params = "minAge", constVal = "true", spec = Null.class)
      }),
      @Or({
        @Spec(path = "minAge", params = "maxAge", defaultVal = "100", spec = LessThanOrEqual.class),
        @Spec(path = "minAge", params = "maxAge", constVal = "true", spec = Null.class)
      })
    },
    and = @Spec(path = "preferredGender", spec = In.class))
public interface MusicianWantedAdSpecification extends AdSpecification<MusicianWantedAdEntity> {}
